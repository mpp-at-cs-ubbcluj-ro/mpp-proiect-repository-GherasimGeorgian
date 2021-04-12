package triatlon.network.utils;

import service.ServiceTriatlon;
import triatlon.services.ITriatlonServices;
import triatlon.network.rpcprotocol.TriatlonClientRpcReflectionWorker;

import java.net.Socket;

public class ChatRpcConcurrentServer extends AbsConcurrentServer {
    private ITriatlonServices chatServer;
    public ChatRpcConcurrentServer(int port, ITriatlonServices chatServer) {
        super(port);
        this.chatServer = chatServer;

        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        TriatlonClientRpcReflectionWorker worker=new TriatlonClientRpcReflectionWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
