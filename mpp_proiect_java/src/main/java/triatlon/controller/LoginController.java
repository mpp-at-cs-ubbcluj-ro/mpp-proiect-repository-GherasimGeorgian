package triatlon.controller;

import javafx.scene.Node;
import javafx.stage.WindowEvent;
import triatlon.services.ITriatlonServices;
import domain.Arbitru;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceTriatlon;
import triatlon.services.TriatlonException;

public class LoginController {
    private Arbitru arbitru = null;
    private ITriatlonServices server;
    private UserPageController upController;
    private Parent mainChatParent;

    public void setServer(ITriatlonServices server){
        this.server=server;
    }

    public void setUserPageController(UserPageController upController) {
        this.upController = upController;
    }
    public void setParent(Parent p){
        mainChatParent=p;
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




                try{
                    String nume = txtUserName.getText();
                    String passwd = txtPass.getText();
                    arbitru = server.getArbitrubyName(nume);

                    server.login(arbitru, upController);
                    // Util.writeLog("User succesfully logged in "+crtUser.getId());
                    Stage stage=new Stage();
                    stage.setTitle("Traitlon Window for " +arbitru.getId());
                    stage.setScene(new Scene(mainChatParent));

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            upController.logout();
                            System.exit(0);
                        }
                    });

                    stage.show();
                    upController.setService(arbitru);
                    //upController.setLoggedFriends();

                    Stage stagex = (Stage) btnLogin.getScene().getWindow();
                    stagex.close();
                }   catch (TriatlonException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("MPP triatlon");
                    alert.setHeaderText("Authentication failure");
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }



            }
        });
    }


}
