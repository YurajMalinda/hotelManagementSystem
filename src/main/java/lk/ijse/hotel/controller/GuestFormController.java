package lk.ijse.hotel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Guest;
import lk.ijse.hotel.dto.tm.GuestTM;
import lk.ijse.hotel.model.GuestModel;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class GuestFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtGender;
    public TextField txtCountry;
    public TextField txtZipcode;
    public TextField txtPassportId;
    public TextField txtUser;
    public TableView <GuestTM> tblGuest;
    public TableColumn colUser;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colGender;
    public TableColumn colCountry;
    public TableColumn colZipcode;
    public TableColumn colPassportId;
    public CheckBox checkMale;
    public CheckBox checkFemale;

    @FXML
    private AnchorPane guestPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblGuest.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtUser.setText(newSelection.getUserId());
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                if (newSelection.getGender().equals("Male")) {
                    checkMale.setSelected(true);
                    checkFemale.setSelected(false);
                } else if (newSelection.getGender().equals("Female")) {
                    checkMale.setSelected(false);
                    checkFemale.setSelected(true);
                } else {
                    checkMale.setSelected(false);
                    checkFemale.setSelected(false);
                }
                txtCountry.setText(newSelection.getCountry());
                txtZipcode.setText(newSelection.getZipCode());
                txtPassportId.setText(newSelection.getPassportId());
            }
        });
    }

    private void setCellValueFactory() {
        colUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colZipcode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        colPassportId.setCellValueFactory(new PropertyValueFactory<>("passportId"));
    }

    private void getAll() {
        try {
            ObservableList<GuestTM> obList = FXCollections.observableArrayList();
            List<Guest> gusList = GuestModel.getAll();

            for (Guest guest : gusList) {
                obList.add(new GuestTM(
                        guest.getUserId(),
                        guest.getId(),
                        guest.getName(),
                        guest.getGender(),
                        guest.getCountry(),
                        guest.getZipCode(),
                        guest.getPassportId()
                ));
            }
            tblGuest.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1) {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) guestPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else if(BackButtonController.backButton == 0){
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) guestPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        try {
            boolean isDeleted = GuestModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String userId = txtUser.getText();
        String id = txtId.getText();
        String name = txtName.getText();
        String gender = "";
        if (checkMale.isSelected()) {
            gender = "Male";
            checkFemale.setSelected(false);
        } else if (checkFemale.isSelected()) {
            gender = "Female";
            checkMale.setSelected(false);
        }
        String country = txtCountry.getText();
        String zipcode = txtZipcode.getText();
        String passportId = txtPassportId.getText();

        try {
            boolean isUpdated = GuestModel.update(userId, id, name, gender, country, zipcode, passportId);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Guest updated!").show();
                getAll();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "Guest Not updated!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            String userId = txtUser.getText();
            String id = txtId.getText();
            String name = txtName.getText();
            String gender = "";
            if (checkMale.isSelected()) {
                gender = "Male";
                checkFemale.setSelected(false);
            } else if (checkFemale.isSelected()) {
                gender = "Female";
                checkMale.setSelected(false);
            }
            String country = txtCountry.getText();
            String zipcode = txtZipcode.getText();
            String passportId = txtPassportId.getText();

            // Validate fields
            validateFields(userId, id, name, gender, country, zipcode, passportId);

            // Validate Guest ID
            validateGuestId(id);

            Guest guest = new Guest(userId, id, name, gender, country, zipcode, passportId);

            // Save guest to database
            boolean isSaved = GuestModel.add(guest);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Guest saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }
    }

    public void validateFields(String userId, String id, String name, String gender, String country, String zipcode, String passportId) {
        // Check if all the required fields are not empty
        if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || country.isEmpty() || zipcode.isEmpty() || passportId.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateGuestId(String id) {
        // Check if Guest ID is valid using a regular expression
        String guestIdRegex = "^G\\d+$";
        if (!id.matches(guestIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid Guest ID starting with 'G' followed by one or more digits (e.g. E001)!");
        }
    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtUser.clear();
        txtPassportId.clear();
        txtZipcode.clear();
        txtCountry.clear();
        checkFemale.setSelected(false);
        checkMale.setSelected(false);
        txtName.clear();
    }

    public void CodeSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            Guest guest = GuestModel.search(txtId.getText());
            if (guest != null) {
                txtUser.setText(guest.getUserId());
                txtId.setText(guest.getId());
                txtName.setText(guest.getName());
                String gender = guest.getGender();
                if (gender != null && gender.equals("Male")) {
                    checkMale.setSelected(true);
                } else if (gender != null && gender.equals("Female")) {
                    checkFemale.setSelected(true);
                }
                txtCountry.setText(guest.getCountry());
                txtZipcode.setText(guest.getZipCode());
                txtPassportId.setText(guest.getPassportId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
