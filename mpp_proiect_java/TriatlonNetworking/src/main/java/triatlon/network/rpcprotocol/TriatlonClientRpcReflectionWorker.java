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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class TriatlonClientRpcReflectionWorker implements Runnable, ITriatlonObserver {
    private ITriatlonServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public TriatlonClientRpcReflectionWorker(ITriatlonServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    public void rezultatReceived(Rezultat rezultat) throws TriatlonException {
        Response resp = new Response.Builder().type(ResponseType.REZULTAT_NOU).data(rezultat).build();
        System.out.println("Rezultat received  " + rezultat);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new TriatlonException("Sending error: " + e);
        }
    }



    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleREQ_FLNAME(Request request){
        System.out.println("handleREQ_FLNAME Request ...");
        String flname=(String) request.data();
        String firstname = flname.split(",")[0];
        String lastname = flname.split(",")[1];
        try {
            Participant participant=server.findParticipantByNumePrenume(firstname,lastname);

            return new Response.Builder().type(ResponseType.GET_ARBITRU).data(participant).build();
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ARBITRU(Request request){
        System.out.println("handleGET_ARBITRU Request ...");
        String udto=(String)request.data();
        try {
            Arbitru arbitru=server.getArbitrubyName(udto);

            return new Response.Builder().type(ResponseType.GET_ARBITRU).data(arbitru).build();
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_PROBA_ARB(Request request){
        System.out.println("handleGET_PROBA_ARB ...");
        long id = (long) request.data();

        try {
            Proba proba = server.getProbaArbitrubyId(id);
            return new Response.Builder().type(ResponseType.GET_PARTICIPANTIDTO).data(proba).build();
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_REZULTAT(Request request){
        System.out.println("handleGET_REZULTAT Request ...");
        Proba udto=(Proba)request.data();
        try {
            List<Rezultat> rezultate=server.filterbyProba(udto);
            return new Response.Builder().type(ResponseType.GET_REZULTAT).data(rezultate).build();
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_PARTICIPANTIDTO(Request request){
        System.out.println("handleGET_PARTICIPANTIDTO Request ...");
        Arbitru udto=(Arbitru)request.data();
        try {
            List<ParticipantDTO> participantDTOS=server.getParticipantiDTO(udto);
            return new Response.Builder().type(ResponseType.GET_PARTICIPANTIDTO).data(participantDTOS).build();
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request ..." + request.type());
        Arbitru udto = (Arbitru) request.data();

        try {
            server.login(udto, this);
            return okResponse;
        } catch (TriatlonException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request) {
        System.out.println("Logout request...");
        Arbitru udto = (Arbitru) request.data();

        try {
            server.logout(udto, this);
            connected = false;
            return okResponse;

        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleSEND_REZULTAT(Request request) {
        System.out.println("SendREzultatRequest ...");
        Rezultat mdto = (Rezultat) request.data();

        try {
            server.sendRezultat(mdto);
            return okResponse;
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }
}