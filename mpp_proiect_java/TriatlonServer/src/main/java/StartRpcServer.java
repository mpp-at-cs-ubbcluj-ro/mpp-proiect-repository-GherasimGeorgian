import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import service.ServiceTriatlon;
//import triatlon.services.ITriatlonServices;
//import triatlon.network.utils.AbstractServer;
//import triatlon.network.utils.ChatRpcConcurrentServer;
//import triatlon.network.utils.ServerException;


public class StartRpcServer {
   // private static int defaultPort=55555;
    public static void main(String[] args) {
//        // UserRepository userRepo=new UserRepositoryMock();
//        Properties serverProps=new Properties();
//        try {
//            serverProps.load(StartRpcServer.class.getResourceAsStream("/triatlonserver.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find chatserver.properties "+e);
//            return;
//        }
//       // UserRepository userRepo=new UserRepositoryJdbc(serverProps);
//       // MessageRepository messRepo=new MessageRepositoryJdbc(serverProps);
//
//        final String url = ApplicationContext.getPROPERTIES().getProperty("database.triatlon.url");
//        final String username= ApplicationContext.getPROPERTIES().getProperty("databse.triatlon.username");
//        final String pasword= ApplicationContext.getPROPERTIES().getProperty("database.triatlon.pasword");
//
//
//        IArbitruRepository arbitruDataBase = new ArbitruDbRepository(ApplicationContext.getPROPERTIES());
//        IParticipantRepository participantDataBase = new ParticipantDbRepository(ApplicationContext.getPROPERTIES());
//        IProbaRepository probaDataBase = new ProbaDbRepository(ApplicationContext.getPROPERTIES());
//        IRezultatRepository rezultatDataBase = new RezultatDbRepository(ApplicationContext.getPROPERTIES(),participantDataBase,probaDataBase);
//
//        ServiceTriatlon service = new ServiceTriatlon(arbitruDataBase,participantDataBase,probaDataBase,rezultatDataBase);
//
//
//        ITriatlonServices chatServerImpl=new TriatlonServicesImpl(service);
//        int chatServerPort=defaultPort;
//        try {
//            chatServerPort = Integer.parseInt(serverProps.getProperty("triatlon.server.port"));
//        }catch (NumberFormatException nef){
//            System.err.println("Wrong  Port Number"+nef.getMessage());
//            System.err.println("Using default port "+defaultPort);
//        }
//        System.out.println("Starting server on port: "+chatServerPort);
//
//
//        AbstractServer server = new ChatRpcConcurrentServer(chatServerPort, chatServerImpl);
//        try {
//            server.start();
//        } catch (ServerException e) {
//            System.err.println("Error starting the server" + e.getMessage());
//        }finally {
//            try {
//                server.stop();
//            }catch(ServerException e){
//                System.err.println("Error stopping server "+e.getMessage());
//            }
//        }

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}
