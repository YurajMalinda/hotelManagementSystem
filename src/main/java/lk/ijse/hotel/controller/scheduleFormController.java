package lk.ijse.hotel.controller;

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
import lk.ijse.hotel.dto.Schedule;
import lk.ijse.hotel.dto.tm.ScheduleTM;
import lk.ijse.hotel.model.ScheduleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class scheduleFormController {
    public AnchorPane schedulePane;
    public TableView <ScheduleTM>tblSchedule;
    public TableColumn colId;
    public TableColumn colDetails;
    public TextField txtId;
    public TextArea txtDetails;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
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
            List<Schedule> scheduleList = ScheduleModel.getAll();

            for (Schedule schedule : scheduleList) {
                obList.add(new ScheduleTM(
                        schedule.getId(),
                        schedule.getDetails()
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
                Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

                Stage stage = (Stage) schedulePane.getScene().getWindow();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(parent));
                stage.centerOnScreen();
            }else{
                Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

                Stage stage = (Stage) schedulePane.getScene().getWindow();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(parent));
                stage.centerOnScreen();
            }
        }

        public void btnDeleteOnAction (ActionEvent actionEvent){
            String id = txtId.getText();
            try {
                boolean isDeleted = ScheduleModel.delete(id);
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

                // Validate fields
                validateFields(id, details);

                // Validate schedule ID
                validateScheduleId(id);

                boolean isUpdated = ScheduleModel.update(id, details);
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

                // Validate fields
                validateFields(id, details);

                // Validate schedule ID
                validateScheduleId(id);

                Schedule schedule = new Schedule(id, details);

                // Save booking to database
                boolean isSaved = ScheduleModel.add(schedule);
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

        public void validateFields(String id, String details) {
            // Check if all the required fields are not empty
            if (id.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }
        }

        public void validateScheduleId(String id) {
            // Check if booking ID is valid using a regular expression
            String scheduleIdRegex = "^S\\d+$";
            if (!id.matches(scheduleIdRegex)) {
                throw new IllegalArgumentException("Please enter a valid schedule ID starting with 'S' followed by one or more digits (e.g. S123)!");
            }
        }

        public void btnClearOnAction (ActionEvent actionEvent){
        txtId.clear();
        txtDetails.clear();
        }

        public void codeSearchOnAction (ActionEvent actionEvent){
            try {
                Schedule schedule = ScheduleModel.search(txtId.getText());
                if (schedule != null) {
                    txtId.setText(schedule.getId());
                    txtDetails.setText(schedule.getDetails());
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something happened!").show();
            }
        }
    }
