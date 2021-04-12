package triatlon.controller;

import domain.Arbitru;
import domain.Participant;
import domain.Proba;
import domain.Rezultat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ServiceTriatlon;
import triatlon.services.ITriatlonObserver;
import triatlon.services.ITriatlonServices;
import triatlon.services.TriatlonException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdaugaRezultatController{


    private Participant participant = null;
    private ITriatlonServices server;
    private Arbitru arbitru;
    public void setService(Participant participant,ITriatlonServices server, Arbitru arbitru) {

        this.participant = participant;
        this.server = server;
        this.arbitru = arbitru;
        initModelProbe();
    }
    @FXML
    private Button btnAdaugaRez;

    @FXML
    private ComboBox comboBoxProbe;

    @FXML
    private TextField txtPunctaj;

    private ObservableList<Proba> modelProbe = FXCollections.observableArrayList();




    private void initModelProbe(){
        //TODO
        try {
            modelProbe.add(server.getProbaArbitrubyId(arbitru.getIdProbaArbitru()));
        }catch(TriatlonException ex){
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    public void initialize() {
        comboBoxProbe.setItems(modelProbe);

        btnAdaugaRez.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(comboBoxProbe.getSelectionModel().getSelectedItem() != null && txtPunctaj.getText() != null) {
                        Proba proba = (Proba) comboBoxProbe.getSelectionModel().getSelectedItem();

                        //TODO
                        Rezultat rez = new Rezultat(proba,participant,Integer.parseInt(txtPunctaj.getText()));

                        server.sendRezultat(rez);

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Rezultatul a fost introdus cu succes!");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Datele introduse sunt invalide!");
                        alert.showAndWait();
                    }
                }catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Datele introduse sunt invalide!");
                    alert.showAndWait();

                }
            }
        });
    }
}
