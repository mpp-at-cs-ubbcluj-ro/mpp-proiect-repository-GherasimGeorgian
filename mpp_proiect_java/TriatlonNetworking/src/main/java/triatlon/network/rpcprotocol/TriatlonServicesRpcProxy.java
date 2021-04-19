package triatlon.network.rpcprotocol;

import domain.*;
import service.ServiceTriatlon;
import triatlon.services.ITriatlonObserver;
import triatlon.services.ITriatlonServices;
import triatlon.services.TriatlonException;
import triatlon.network.dto.ArbitruDTO;
import triatlon.network.dto.DTOUtils;
import triatlon.network.dto.RezultatDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TriatlonServicesRpcProxy implements ITriatlonServices {
    private String host;
    private int port;

    private ITriatlonObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;


    private ServiceTriatlon service;


    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public TriatlonServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public void login(Arbitru arbitru, ITriatlonObserver client) throws TriatlonException {

        Request req=new Request.Builder().type(RequestType.LOGIN).data(arbitru).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new TriatlonException(err);
        }
    }

    public void sendRezultat(Rezultat message) throws TriatlonException {

        Request req=new Request.Builder().type(RequestType.SEND_REZULTAT).data(message).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
    }

    public void logout(Arbitru arbitru, ITriatlonObserver client) throws TriatlonException {

        Request req=new Request.Builder().type(RequestType.LOGOUT).data(arbitru).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
    }
    public Proba getProbaArbitrubyId(long id) throws TriatlonException {
        Request req=new Request.Builder().type(RequestType.GET_PROBA_ARB).data(id).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
        Proba frDTO=(Proba)response.data();
        return frDTO;
    }
    public List<Rezultat> filterbyProba(Proba proba)throws TriatlonException {

        Request req=new Request.Builder().type(RequestType.GET_REZULTAT).data(proba).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
        List<Rezultat> frDTO=(List<Rezultat>)response.data();
        return frDTO;
    }


    public List<ParticipantDTO> getParticipantiDTO(Arbitru arbitru) throws TriatlonException {

        Request req=new Request.Builder().type(RequestType.GET_PARTICIPANTIDTO).data(arbitru).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
        List<ParticipantDTO> frDTO=(List<ParticipantDTO>)response.data();
        return frDTO;
    }
    public Participant findParticipantByNumePrenume(String firstName, String lastName) throws TriatlonException{
        String flname = firstName + "," + lastName;
        Request req=new Request.Builder().type(RequestType.REQ_FLNAME).data(flname).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
        Participant arb=(Participant)response.data();

        return arb;
    }
    public Arbitru getArbitrubyName(String nume) throws TriatlonException{
        initializeConnection();
        Request req=new Request.Builder().type(RequestType.GET_ARBITRU).data(nume).build();
        System.out.println("cevaaaaa req" +req.data().toString());
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
        Arbitru arb=(Arbitru)response.data();

        return arb;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws TriatlonException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TriatlonException("Error sending object "+e);
        }

    }

    private Response readResponse() throws TriatlonException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws TriatlonException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){

        if (response.type()== ResponseType.REZULTAT_NOU){
            Rezultat rez= (Rezultat) response.data();
            try {
                client.rezultatReceived(rez);
            } catch (TriatlonException| RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.REZULTAT_NOU;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}