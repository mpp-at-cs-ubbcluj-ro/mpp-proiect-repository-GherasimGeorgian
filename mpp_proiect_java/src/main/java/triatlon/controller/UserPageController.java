package triatlon.controller;

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
import triatlon.domain.*;
import triatlon.service.ServiceTriatlon;
import triatlon.utils.events.ChangeEvent;
import triatlon.utils.observer.Observer;

import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserPageController implements Observer<ChangeEvent> {

    private ServiceTriatlon service;
    private Arbitru arbitru = null;

    public void setService(ServiceTriatlon service,Arbitru arbitru) {
        this.service=service;
        this.arbitru = arbitru;
        service.addObserver(this);
        setNumePrenumeArbitru();
        setPrietenieDTOTable();
        initModelProbe();

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
    @Override
    public void update(ChangeEvent messageTaskChangeEvent) {
        initModel();
        if(comboBoxProba.getSelectionModel().getSelectedItem() != null){
            initModelRezultate();
        }

    }
    private void initModel() {
        modelParticipantDTO.setAll(StreamSupport.stream(service.getParticipantiDTO().spliterator(), false)
                .collect(Collectors.toList()));
    }
    private void initModelProbe(){
        modelProbe.setAll(StreamSupport.stream(service.getProbe().spliterator(), false)
                .collect(Collectors.toList()));
    }

    private void initModelRezultate(){
        Proba proba = (Proba) comboBoxProba.getSelectionModel().getSelectedItem();
        modelRezultate.setAll(StreamSupport.stream(service.filterbyProba(proba).spliterator(), false)
                .collect(Collectors.toList()));
    }

    private void setPrietenieDTOTable() {
        modelParticipantDTO.setAll(StreamSupport.stream(service.getParticipantiDTO().spliterator(), false)
                .collect(Collectors.toList()));

        tableColumnPDTOFirstName.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, String>("firstName"));
        tableColumnDatePDTOLastName.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, String>("lastName"));
        tableColumnDatePDTOPunctaj.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, Integer>("punctaj"));
        tableViewParticipantDTO.setItems(modelParticipantDTO);

    }

    private void setRezultateTable(){
        Proba proba = (Proba) comboBoxProba.getSelectionModel().getSelectedItem();
        modelRezultate.setAll(StreamSupport.stream(service.filterbyProba(proba).spliterator(), false)
                .collect(Collectors.toList()));

        tableColumnfirstName.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getParticipant().getFirstName()));;
        tableColumnlastName.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getParticipant().getLastName()));
        tableColumntipProba.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getProba().getTipProba()));
        tableColumnpunctaj.setCellValueFactory(new PropertyValueFactory<Rezultat, Integer>("numarPuncte"));
        tableViewRezultate.setItems(modelRezultate);

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

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/views/adauga_rezultat.fxml"));
                        AnchorPane root=fxmlLoader.load();
                        AdaugaRezultatController adaugaRezultatController = fxmlLoader.getController();
                        Participant participant_selectat = service.findParticipantByNumePrenume(row.getItem().getFirstName(),row.getItem().getLastName());
                        adaugaRezultatController.setService(service,participant_selectat);
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
        btnlogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btnlogout.getScene().getWindow();
                stage.close();
                try {

                    Stage stageLogin = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/login.fxml"));
                    AnchorPane root = loader.load();

                    LoginController ctrl = loader.getController();
                    ctrl.setService(service);
                    stageLogin.setScene(new Scene(root, 700, 500));
                    stageLogin.setTitle("LoginPage");
                    stageLogin.show();
                }catch(Exception ex){

                }

            }
        });

    }



    public void setNumePrenumeArbitru(){
        lblNume.setText(arbitru.getFirstName());
        lblPrenume.setText(arbitru.getLastName());
    }

}
