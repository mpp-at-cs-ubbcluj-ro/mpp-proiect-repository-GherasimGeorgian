package triatlon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import triatlon.config.ApplicationContext;

import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Rezultat;
import triatlon.repository.IParticipantRepository;
import triatlon.repository.IProbaRepository;
import triatlon.repository.database.ArbitruDbRepository;
import triatlon.repository.IRepository;
import triatlon.repository.database.ParticipantDbRepository;
import triatlon.repository.database.ProbaDbRepository;
import triatlon.repository.database.RezultatDbRepository;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final String url = ApplicationContext.getPROPERTIES().getProperty("database.triatlon.url");
        final String username= ApplicationContext.getPROPERTIES().getProperty("databse.triatlon.username");
        final String pasword= ApplicationContext.getPROPERTIES().getProperty("database.triatlon.pasword");


        IRepository<Long,Arbitru> arbitruDataBase = new ArbitruDbRepository(ApplicationContext.getPROPERTIES());

        IParticipantRepository participantDataBase = new ParticipantDbRepository(ApplicationContext.getPROPERTIES());
        IProbaRepository probaDataBase = new ProbaDbRepository(ApplicationContext.getPROPERTIES());

        IRepository<Long, Rezultat> rezultatDataBase = new RezultatDbRepository(ApplicationContext.getPROPERTIES(),participantDataBase,probaDataBase);

        arbitruDataBase.findAll().forEach(System.out::println);
        participantDataBase.findAll().forEach(System.out::println);
        probaDataBase.findAll().forEach(System.out::println);
       // Proba pr = probaDataBase.findOne((long)454543525);
       // System.out.println(pr);
        rezultatDataBase.findAll().forEach(System.out::println);















        //rezultatDataBase.findAll().forEach(System.out::println);

        // Repository<Long, Arbitru> arbitruDataBase =
      //          new ArbitruDB(url,username, pasword,  new ArbitruValidator());

//        Repository<Long, Participant> participantDataBase =
  //              new ParticipantDB(url,username, pasword,  new ParticipantValidator());

        //Repository<Tuple<Long,Integer>, Rezultat> rezultatDataBase =
          //      new RezultatDB(url,username, pasword,  new RezultatValidator());



        //ServiceTriatlon service = new ServiceTriatlon(arbitruDataBase,participantDataBase,rezultatDataBase);



       // FXMLLoader loader=new FXMLLoader();
        //loader.setLocation(getClass().getResource("/views/login.fxml"));
        //AnchorPane root=loader.load();


        //LoginController ctrl=loader.getController();
        //ctrl.setService(service);
        //primaryStage.setScene(new Scene(root, 700, 400));
        //primaryStage.setTitle("LoginPage");
        //primaryStage.show();

    }
}
