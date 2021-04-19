//package triatlon;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import repository.IArbitruRepository;
//import repository.IParticipantRepository;
//import repository.IProbaRepository;
//import repository.IRezultatRepository;
//import repository.database.ArbitruDbRepository;
//import repository.database.ParticipantDbRepository;
//import repository.database.ProbaDbRepository;
//import repository.database.RezultatDbRepository;
//import triatlon.config.ApplicationContext;
//
//import triatlon.controller.LoginController;
//import service.ServiceTriatlon;
//
//
//public class Main extends Application {
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        final String url = ApplicationContext.getPROPERTIES().getProperty("database.triatlon.url");
//        final String username= ApplicationContext.getPROPERTIES().getProperty("databse.triatlon.username");
//        final String pasword= ApplicationContext.getPROPERTIES().getProperty("database.triatlon.pasword");
//
//
//        IArbitruRepository arbitruDataBase = new ArbitruDbRepository(ApplicationContext.getPROPERTIES());
//
//        IParticipantRepository participantDataBase = new ParticipantDbRepository(ApplicationContext.getPROPERTIES());
//        IProbaRepository probaDataBase = new ProbaDbRepository(ApplicationContext.getPROPERTIES());
//
//        IRezultatRepository rezultatDataBase = new RezultatDbRepository(ApplicationContext.getPROPERTIES(),participantDataBase,probaDataBase);
//
////        //findAll
////        arbitruDataBase.findAll().forEach(System.out::println);
////        participantDataBase.findAll().forEach(System.out::println);
////        probaDataBase.findAll().forEach(System.out::println);
////        rezultatDataBase.findAll().forEach(System.out::println);
////
////        //findone
////         Proba pr = probaDataBase.findOne((long)454543525);
////         System.out.println("Am gasit proba: "+pr);
////
////         Arbitru ab = arbitruDataBase.findOne((long)262324256);
////         System.out.println("Am gasit arbitrul: "+ab);
////
////         Participant pt = participantDataBase.findOne((long)32562362);
////         System.out.println("Am gasit participantul: " + pt);
////
////         Rezultat rez = rezultatDataBase.findOne((long)12);
////         System.out.println("Am gasit rezultatul: " + rez);
//
//        //save
////        try{
////            Proba pr1 = new Proba((long)44242,"alergare",3);
////            probaDataBase.save(pr1);
////        }catch(Exception ex){
////
////        }
////        try{
////            Participant part1 = new Participant((long)3242,"rares","kiky",54);
////            participantDataBase.save(part1);
////        }catch(Exception ex){
////
////        }
//
//
//        //rezultatDataBase.findAll().forEach(System.out::println);
//
//        // Repository<Long, Arbitru> arbitruDataBase =
//      //          new ArbitruDB(url,username, pasword,  new ArbitruValidator());
//
////        Repository<Long, Participant> participantDataBase =
//  //              new ParticipantDB(url,username, pasword,  new ParticipantValidator());
//
//        //Repository<Tuple<Long,Integer>, Rezultat> rezultatDataBase =
//          //      new RezultatDB(url,username, pasword,  new RezultatValidator());
//
//
//
//        ServiceTriatlon service = new ServiceTriatlon(arbitruDataBase,participantDataBase,probaDataBase,rezultatDataBase);
//
//        service.getParticipantiDTO();
//
//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("/views/login.fxml"));
//        AnchorPane root=loader.load();
//
//
//        LoginController ctrl=loader.getController();
//        //ctrl.setService(service);
//        primaryStage.setScene(new Scene(root, 700, 400));
//        primaryStage.setTitle("LoginPage");
//        primaryStage.show();
//
//    }
//}
