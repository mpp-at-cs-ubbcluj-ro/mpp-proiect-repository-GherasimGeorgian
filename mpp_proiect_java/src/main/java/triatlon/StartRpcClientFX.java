package triatlon;

import triatlon.services.ITriatlonServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.IArbitruRepository;
import repository.IParticipantRepository;
import repository.IProbaRepository;
import repository.IRezultatRepository;
import repository.database.ArbitruDbRepository;
import repository.database.ParticipantDbRepository;
import repository.database.ProbaDbRepository;
import repository.database.RezultatDbRepository;
import triatlon.config.ApplicationContext;
import triatlon.controller.LoginController;
import triatlon.controller.UserPageController;
import triatlon.network.rpcprotocol.TriatlonServicesRpcProxy;
import service.ServiceTriatlon;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClientFX extends Application {

    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    @Override
    public void start(Stage primaryStage) throws  Exception{
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/triatlonclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find triatlonclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("triatlon.server.host", defaultServer);
        int serverPort = defaultChatPort;
        try {
            serverPort = Integer.parseInt(clientProps.getProperty("triatlon.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);



        final String url = ApplicationContext.getPROPERTIES().getProperty("database.triatlon.url");
        final String username= ApplicationContext.getPROPERTIES().getProperty("databse.triatlon.username");
        final String pasword= ApplicationContext.getPROPERTIES().getProperty("database.triatlon.pasword");



        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        AnchorPane root=loader.load();


        FXMLLoader cloader = new FXMLLoader();
        cloader.setLocation(getClass().getResource("/views/userpage.fxml"));


        Parent croot=cloader.load();
        UserPageController upCtrl =
                cloader.<UserPageController>getController();

        ITriatlonServices server = new TriatlonServicesRpcProxy(serverIP, serverPort);

        upCtrl.setServer(server);





        LoginController ctrl=loader.getController();

        ctrl.setServer(server);
        ctrl.setParent(croot);
        ctrl.setUserPageController(upCtrl);
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.setTitle("LoginPage");
        primaryStage.show();

        System.out.println("ceva");
    }
}
