//package triatlon.controller;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import triatlon.domain.Arbitru;
//import triatlon.service.ServiceTriatlon;
//import triatlon.utils.password.PasswordHashing;
//
//public class LoginController {
//    private ServiceTriatlon service;
//    private Arbitru arbitru = null;
//
//    public void setService(ServiceTriatlon service) {
//        this.service=service;
//    }
//
//    //textfields
//    @FXML
//    TextField txtEmail;
//    @FXML
//    PasswordField txtPass;
//
//
//    //buttons
//    @FXML
//    Button btnLogin;
//
//    @FXML
//    public void initialize() {
//        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if(txtEmail.getText().isEmpty() || txtPass.getText().isEmpty()) {
//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.setTitle("Warning");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Emailul si parola nu trebuie sa fie nule!");
//                    alert.showAndWait();
//                }
//                else{
//                    arbitru = getArbitrubyEmailandPass(txtEmail.getText(),txtPass.getText());// PasswordHashing.doHashing(txtPass.getText()));
//                    if(arbitru == null){
//                        Alert alert = new Alert(Alert.AlertType.WARNING);
//                        alert.setTitle("Warning");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Userul introdus nu exista!");
//                        alert.showAndWait();
//                        txtEmail.clear();
//                        txtPass.clear();
//                    }
//                    else{
//                        try {
//                            FXMLLoader fxmlLoader = new FXMLLoader();
//                            fxmlLoader.setLocation(getClass().getResource("/views/userpage.fxml"));
//                            AnchorPane root = fxmlLoader.load();
//
//                            UserPageController ctrl = fxmlLoader.getController();
//                            ctrl.setService(service,arbitru);
//
//                            Stage stagePageUser = new Stage();
//                            Scene scene = new Scene(root, 1200, 562);
//
//                            stagePageUser.setTitle("UserPage");
//                            stagePageUser.setScene(scene);
//                            stagePageUser.setResizable(false);
//                            stagePageUser.show();
//                        }catch(Exception ex){
//                            System.out.println(ex);
//                        }
//
//                        Stage stage = (Stage) txtEmail.getScene().getWindow();
//                        stage.close();
//                    }
//                }
//            }
//        });
//    }
//    private Arbitru getArbitrubyEmailandPass(String email,String password){
//        Arbitru accUser = null;
//        try{
//            accUser = service.findArbitruByEmailandPassword(email,password);
//        }catch(Exception e){
//            accUser = null;
//        }
//
//        return accUser;
//    }
//
//
//}
