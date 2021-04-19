package triatlon.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import triatlon.services.ITriatlonObserver;
import triatlon.services.ITriatlonServices;
import triatlon.services.TriatlonException;
import domain.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ServiceTriatlon;
import utils.events.ChangeEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserPageController  extends UnicastRemoteObject implements ITriatlonObserver, Serializable {



    private Arbitru arbitru = null;
    private ITriatlonServices server;

    public UserPageController() throws RemoteException {

    }

    public void setService(Arbitru arbitru) {
        this.arbitru = arbitru;
        setNumePrenumeArbitru();
        setParticipantiDTOTable();
        initModelProbe();

    }
    public void setServer(ITriatlonServices server){
        this.server=server;
    }


    public void rezultatReceived(Rezultat rezultat) throws TriatlonException {
        Platform.runLater(()->{
            if (rezultat.getProba().getId().toString().equals(String.valueOf(arbitru.getIdProbaArbitru())))
                modelRezultate.add(rezultat);
            int i = 0;
            for (ParticipantDTO part : modelParticipantDTO) {
                if (part.getFirstName().equals(rezultat.getParticipant().getFirstName()) &&
                        part.getLastName().equals(rezultat.getParticipant().getLastName())) {
                    modelParticipantDTO.set(i, new ParticipantDTO(part.getFirstName(), part.getLastName(), part.getPunctaj() + rezultat.getNumarPuncte()));
                    break;
                }
                i++;
            }
        });
    }

    @FXML
    Label lblNume;

    @FXML
    Label lblPrenume;

    @FXML
    ComboBox comboBoxProba;

    @FXML
    Button btnlogout;

    ObservableList<ParticipantDTO> modelParticipantDTO = FXCollections.observableArrayList();


    //tableParticipanti
    @FXML
    TableView<ParticipantDTO> tableViewParticipantDTO;
    @FXML
    TableColumn<ParticipantDTO, String> tableColumnPDTOFirstName;
    @FXML
    TableColumn<ParticipantDTO, String> tableColumnDatePDTOLastName;
    @FXML
    TableColumn<ParticipantDTO, Integer> tableColumnDatePDTOPunctaj;


    //tableRezultate
    @FXML
    TableView<Rezultat> tableViewRezultate;
    @FXML
    TableColumn<Rezultat, String> tableColumnfirstName;
    @FXML
    TableColumn<Rezultat, String> tableColumnlastName;
    @FXML
    TableColumn<Rezultat, String> tableColumntipProba;
    @FXML
    TableColumn<Rezultat, Integer> tableColumnpunctaj;

    private ObservableList<Rezultat> modelRezultate = FXCollections.observableArrayList();
    private ObservableList<Proba> modelProbe = FXCollections.observableArrayList();


    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {
        try {
            server.logout(arbitru, this);
        } catch (TriatlonException e) {
            System.out.println("Logout error " + e);
        }

    }




    private void initModelProbe(){
        try {
            modelProbe.add(server.getProbaArbitrubyId(arbitru.getIdProbaArbitru()));
        } catch (TriatlonException e) {
            System.out.println("initModelProbe error " + e);
        }
    }



    private void setParticipantiDTOTable() {
        try {
            modelParticipantDTO.setAll(server.getParticipantiDTO(arbitru));

            tableColumnPDTOFirstName.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, String>("firstName"));
            tableColumnDatePDTOLastName.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, String>("lastName"));
            tableColumnDatePDTOPunctaj.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, Integer>("punctaj"));
            tableViewParticipantDTO.setItems(modelParticipantDTO);
        }catch (TriatlonException ex){

        }
    }

    private void setRezultateTable(){
        try {
            Proba proba = (Proba) comboBoxProba.getSelectionModel().getSelectedItem();

            modelRezultate.setAll(server.filterbyProba(proba));

            tableColumnfirstName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getParticipant().getFirstName()));
            tableColumnlastName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getParticipant().getLastName()));
            tableColumntipProba.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProba().getTipProba()));
            tableColumnpunctaj.setCellValueFactory(new PropertyValueFactory<Rezultat, Integer>("numarPuncte"));
            tableViewRezultate.setItems(modelRezultate);
        }catch (TriatlonException ex){
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    public void initialize() {
        comboBoxProba.setItems(modelProbe);


        comboBoxProba.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                try {


                    Proba proba = (Proba)comboBoxProba.getSelectionModel().getSelectedItem();
                    setRezultateTable();

                }catch(Exception ex){

                }
            }
        });

        tableViewParticipantDTO.setRowFactory( tv -> {
            TableRow<ParticipantDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ParticipantDTO rowData = row.getItem();
                    Participant participant_selectat = null;
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/views/adauga_rezultat.fxml"));
                        AnchorPane root=fxmlLoader.load();
                        AdaugaRezultatController adaugaRezultatController = fxmlLoader.getController();
                        try {
                             participant_selectat = server.findParticipantByNumePrenume(row.getItem().getFirstName(), row.getItem().getLastName());
                        }catch(TriatlonException ex){
                            System.out.println(ex.getMessage());
                        }
                        adaugaRezultatController.setService(participant_selectat,server,arbitru);
                        Scene scene = new Scene(root, 400, 400);

                        Stage stagePageUser = new Stage();

                        stagePageUser.setTitle("UserPage");
                        stagePageUser.setScene(scene);
                        stagePageUser.setResizable(false);
                        stagePageUser.show();

                    } catch (IOException e) {
                        Logger logger = Logger.getLogger(getClass().getName());
                        logger.log(Level.SEVERE, "Failed to create new Window.", e);
                    }
                }
            });
            return row ;
        });


    }



    public void setNumePrenumeArbitru(){
        lblNume.setText(arbitru.getFirstName());
        lblPrenume.setText(arbitru.getLastName());
    }

}
