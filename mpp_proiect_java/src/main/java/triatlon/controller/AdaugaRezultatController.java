package triatlon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import triatlon.domain.*;
import triatlon.service.ServiceTriatlon;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdaugaRezultatController{

    private ServiceTriatlon service;
    private Participant participant = null;

    public void setService(ServiceTriatlon service,Participant participant) {
        this.service=service;
        this.participant = participant;
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
        modelProbe.setAll(StreamSupport.stream(service.getProbe().spliterator(), false)
                .collect(Collectors.toList()));
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
                        service.adaugaRezultat(proba,participant,Integer.parseInt(txtPunctaj.getText()));
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
