package triatlon.network.protobuffprotocol;


import com.squareup.okhttp.Response;
import domain.*;
import triatlon.services.ITriatlonObserver;
import triatlon.services.ITriatlonServices;
import triatlon.services.TriatlonException;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static triatlon.network.protobuffprotocol.TriatlonProtobufs.TriatlonResponse.Type.newRezultat;

public final class ProtoTriatlonProxy implements ITriatlonServices {
    private String host;
    private int port;

    private ITriatlonObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<TriatlonProtobufs.TriatlonResponse> qresponses;
    private volatile boolean finished;

    public ProtoTriatlonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<TriatlonProtobufs.TriatlonResponse>();
    }



    public List<Rezultat> filterbyProba(Proba proba) throws  TriatlonException{
        sendRequest(ProtoUtils.createfilterbyProbaRequest(proba));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
        List<Rezultat> listaRez=ProtoUtils.getfilterbyProbaRequest(response);
        return listaRez;
    }
    public List<ParticipantDTO> getParticipantiDTO(Arbitru arbitru)throws TriatlonException{
        sendRequest(ProtoUtils.createGetParticipantiDTORequest(arbitru));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
        List<ParticipantDTO> listaPartDTO=ProtoUtils.getParticipantiDTO(response);
        return listaPartDTO;
    }







    public void login(Arbitru user, ITriatlonObserver client) throws TriatlonException {
        //initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(user));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Ok){
            this.client=client;
            return;
        }
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            closeConnection();
            throw new TriatlonException(errorText);
        }
    }

    public void sendRezultat(Rezultat rezultat) throws TriatlonException {
        System.out.println("Suntem in proto");
        //System.out.println("Am primit rezultatul:"+ rezultat.toString());

        sendRequest(ProtoUtils.createSendRezultatRequest(rezultat));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
    }

    public void logout(Arbitru user, ITriatlonObserver client) throws TriatlonException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        closeConnection();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
    }
    public Arbitru getArbitrubyName(String nume) throws TriatlonException {
        initializeConnection();

        sendRequest(ProtoUtils.creategetArbitrubyNameRequest(nume));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
        Arbitru arbitru =ProtoUtils.getArbitru(response);
        return arbitru;
    }
    public Participant findParticipantByNumePrenume(String firstName, String lastName) throws TriatlonException{
        String stringConcat = firstName+'|' +lastName;
        sendRequest(ProtoUtils.createfindParticipantByNumePrenumeRequest(stringConcat));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
        Participant participant =ProtoUtils.getParticipant(response);
        return participant;
    }

    public Proba getProbaArbitrubyId(long id) throws TriatlonException{
        sendRequest(ProtoUtils.creategetProbaArbitrubyIdRequest(id));
        TriatlonProtobufs.TriatlonResponse response=readResponse();
        if (response.getType()==TriatlonProtobufs.TriatlonResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new TriatlonException(errorText);
        }
        Proba proba =ProtoUtils.getProba(response);
        return proba;
    }
//    public User[] getLoggedFriends(User user) throws ChatException {
//        sendRequest(ProtoUtils.createLoggedFriendsRequest(user));
//        ChatProtobufs.ChatResponse response=readResponse();
//        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
//            String errorText=ProtoUtils.getError(response);
//            throw new ChatException(errorText);
//        }
//        User[] friends=ProtoUtils.getFriends(response);
//        return friends;
//    }

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

    private void sendRequest(TriatlonProtobufs.TriatlonRequest request)throws TriatlonException{
        try {
            System.out.println("Sending request ..."+request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new TriatlonException("Error sending object "+e);
        }

    }

    private TriatlonProtobufs.TriatlonResponse readResponse() throws TriatlonException{
        TriatlonProtobufs.TriatlonResponse response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws TriatlonException{
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            //output.flush();
            input=connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
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


    private void handleUpdate(TriatlonProtobufs.TriatlonResponse updateResponse){
        switch (updateResponse.getType()){
//            case FriendLoggedIn:{
//                User friend=ProtoUtils.getUser(updateResponse);
//                System.out.println("Friend logged in "+friend);
//                try {
//                    client.friendLoggedIn(friend);
//                } catch (ChatException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//            case FriendLoggedOut:{
//                User friend=ProtoUtils.getUser(updateResponse);
//                System.out.println("Friend logged out "+friend);
//                try {
//                    client.friendLoggedOut(friend);
//                } catch (ChatException e) {
//                    e.printStackTrace();
//                }
//
//                break;
//            }
            case newRezultat:{
                Rezultat rezultat=ProtoUtils.getRezultat(updateResponse);
                try {
                    client.rezultatReceived(rezultat);
                } catch (TriatlonException| RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }

        }

    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    TriatlonProtobufs.TriatlonResponse response=TriatlonProtobufs.TriatlonResponse.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }else{
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private boolean isUpdateResponse(TriatlonProtobufs.TriatlonResponse.Type type){
        switch (type){
//            case FriendLoggedIn:  return true;
//            case FriendLoggedOut: return true;
            case newRezultat:return true;
        }
        return false;
    }
}
