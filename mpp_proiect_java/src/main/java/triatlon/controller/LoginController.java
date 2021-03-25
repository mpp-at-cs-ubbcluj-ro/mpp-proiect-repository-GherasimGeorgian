package triatlon.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import triatlon.domain.Arbitru;
import triatlon.service.ServiceTriatlon;
import triatlon.utils.password.PasswordHashing;

public class LoginController {
    private ServiceTriatlon service;
    private Arbitru arbitru = null;

    public void setService(ServiceTriatlon service) {
        this.service=service;
    }

    //textfields
    @FXML
    TextField txtUserName;
    @FXML
    PasswordField txtPass;


    //buttons
    @FXML
    Button btnLogin;

    @FXML
    public void initialize() {
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (txtUserName.getText().isEmpty() || txtPass.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Emailul si parola nu trebuie sa fie nule!");
                        alert.showAndWait();
                    } else {
                        boolean rez = getArbitrubyUsernameandPass(txtUserName.getText(), txtPass.getText());// PasswordHashing.doHashing(txtPass.getText()));
                        if (rez == false) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText(null);
                            alert.setContentText("Userul introdus nu exista!");
                            alert.showAndWait();
                            txtUserName.clear();
                            txtPass.clear();
                        } else {
                            try {
                                arbitru = service.getArbitruByName(txtUserName.getText());
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/views/userpage.fxml"));
                                AnchorPane root = fxmlLoader.load();

                                UserPageController ctrl = fxmlLoader.getController();
                                ctrl.setService(service, arbitru);

                                Stage stagePageUser = new Stage();
                                Scene scene = new Scene(root, 1200, 562);

                                stagePageUser.setTitle("UserPage");
                                stagePageUser.setScene(scene);
                                stagePageUser.setResizable(false);
                                stagePageUser.show();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                            Stage stage = (Stage) txtUserName.getScene().getWindow();
                            stage.close();
                        }
                    }
                }catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Date introduse sunt invalide!");
                    alert.showAndWait();
                    txtUserName.clear();
                    txtPass.clear();
                }
            }
        });
    }
    private boolean getArbitrubyUsernameandPass(String email,String password){
        boolean rez = false;
        try{
            rez = service.loginArbitru(email,password);
            return rez;
        }catch(Exception e){
            return false;
        }


    }


}
