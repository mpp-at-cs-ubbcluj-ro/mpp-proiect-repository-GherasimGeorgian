package triatlon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import triatlon.config.ApplicationContext;
import triatlon.controller.LoginController;
import triatlon.domain.Arbitru;
import triatlon.domain.validators.ArbitruValidator;
import triatlon.repository.Repository;
import triatlon.repository.database.ArbitruDB;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final String url = ApplicationContext.getPROPERTIES().getProperty("database.triatlon.url");
        final String username= ApplicationContext.getPROPERTIES().getProperty("databse.triatlon.username");
        final String pasword= ApplicationContext.getPROPERTIES().getProperty("database.triatlon.pasword");

        Repository<Long, Arbitru> arbitruDataBase =
                new ArbitruDB(url,username, pasword,  new ArbitruValidator());

        arbitruDataBase.findAll().forEach(System.out::println);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        AnchorPane root=loader.load();


        LoginController ctrl=loader.getController();
        //ctrl.setService(serviceUser);
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.setTitle("LoginPage");
        primaryStage.show();

    }
}
