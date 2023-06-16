package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.ScheduleBO;
import lk.ijse.hotel.dto.ScheduleDTO;
import lk.ijse.hotel.view.tdm.ScheduleTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScheduleFormController {
    public AnchorPane schedulePane;
    public TableView <ScheduleTM>tblSchedule;
    public TableColumn colId;
    public TableColumn colDetails;
    public TextField txtId;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    public TextField txtDetails;
    
    ScheduleBO scheduleBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SCHEDULE_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtDetails.clear();
        txtId.setDisable(true);
        txtDetails.setDisable(true);
        txtId.setEditable(false);
        txtDetails.setOnAction(event -> btnAdd.fire());
    }

    private void setSelectToTxt() {
        tblSchedule.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtDetails.setText(newSelection.getDetails());
            }
        });
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
    }

    private void getAll () {
        try {
            ObservableList<ScheduleTM> obList = FXCollections.observableArrayList();
            List<ScheduleDTO> scheduleDTOList = scheduleBO.getAllSchedules();

            for (ScheduleDTO scheduleDTO : scheduleDTOList) {
                obList.add(new ScheduleTM(
                        scheduleDTO.getId(),
                        scheduleDTO.getDetails()
                ));
            }
            tblSchedule.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

        public void btnBackOnAction (ActionEvent actionEvent) throws IOException {
            if(BackButtonController.backButton == 1){
                Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

                Stage stage = (Stage) schedulePane.getScene().getWindow();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(parent));
                stage.centerOnScreen();
            }else{
                Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

                Stage stage = (Stage) schedulePane.getScene().getWindow();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(parent));
                stage.centerOnScreen();
            }
        }

        public void btnDeleteOnAction (ActionEvent actionEvent){
            String id = txtId.getText();
            try {
                boolean isDeleted = scheduleBO.deleteSchedule(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                    getAll();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
        }

        public void btnUpdateOnAction (ActionEvent actionEvent){
            try {
                String id = txtId.getText();
                String details = txtDetails.getText();

                if (id.isEmpty() || details.isEmpty()) {
                    throw new IllegalArgumentException("Please fill out all the required fields!");
                }

                boolean isUpdated = scheduleBO.updateSchedule(new ScheduleDTO(id, details));
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Booking updated!").show();
                    getAll();
                }
            } catch (IllegalArgumentException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
        }

        public void btnAddOnAction (ActionEvent actionEvent){
            try {
                String id = txtId.getText();
                String details = txtDetails.getText();

                if (id.isEmpty() || details.isEmpty()) {
                    throw new IllegalArgumentException("Please fill out all the required fields!");
                }
                // Save booking to database
                boolean isSaved = scheduleBO.addSchedule(new ScheduleDTO(id, details));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Booking saved!").show();
                    getAll();
                }
            } catch (IllegalArgumentException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
            }
        }

        public void codeSearchOnAction (ActionEvent actionEvent){
            try {
                ScheduleDTO scheduleDTO = scheduleBO.searchSchedule(txtId.getText());
                if (scheduleDTO != null) {
                    txtId.setText(scheduleDTO.getId());
                    txtDetails.setText(scheduleDTO.getDetails());
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something happened!").show();
            }
        }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtDetails.clear();
        txtId.setDisable(false);
        txtId.setText(generateNewScheduleId());
        txtDetails.requestFocus();
        txtDetails.setDisable(false);
        txtId.setEditable(false);
        tblSchedule.getSelectionModel().clearSelection();
    }

    private String generateNewScheduleId() {
        try {
            return scheduleBO.generateNewScheduleID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblSchedule.getItems().isEmpty()) {
            return "S00-001";
        } else {
            String id = getLastScheduleId();
            int newScheduleId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S00-%03d", newScheduleId);
        }
    }

    private String getLastScheduleId() {
        List<ScheduleTM> tempScheduleList = new ArrayList<>(tblSchedule.getItems());
        Collections.sort(tempScheduleList);
        return tempScheduleList.get(tempScheduleList.size() - 1).getId();
    }
}
