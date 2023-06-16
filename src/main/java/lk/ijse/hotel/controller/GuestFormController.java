package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.GuestBO;
import lk.ijse.hotel.dto.GuestDTO;
import lk.ijse.hotel.view.tdm.GuestTM;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
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
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;

    @FXML
    private AnchorPane guestPane;

    GuestBO guestBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.GUEST_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtUser.clear();
        txtPassportId.clear();
        txtZipcode.clear();
        txtCountry.clear();
        checkFemale.setSelected(false);
        checkMale.setSelected(false);
        txtName.clear();
        txtId.setDisable(true);
        txtId.setEditable(false);
        txtUser.setDisable(true);
        txtPassportId.setDisable(true);
        txtZipcode.setDisable(true);
        txtCountry.setDisable(true);
        checkMale.setDisable(true);
        checkFemale.setDisable(true);
        txtName.setDisable(true);
        btnUpdate.setDisable(true);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        txtPassportId.setOnAction(event -> btnAdd.fire());
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
            List<GuestDTO> gusList = guestBO.getAllGuests();

            for (GuestDTO guestDTO : gusList) {
                obList.add(new GuestTM(
                        guestDTO.getUserId(),
                        guestDTO.getId(),
                        guestDTO.getName(),
                        guestDTO.getGender(),
                        guestDTO.getCountry(),
                        guestDTO.getZipCode(),
                        guestDTO.getPassportId()
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
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) guestPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else if(BackButtonController.backButton == 0){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) guestPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        try {
            boolean isDeleted = guestBO.deleteGuest(id);
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

        if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || country.isEmpty() || zipcode.isEmpty() || passportId.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = guestBO.updateGuest(new GuestDTO(userId, id, name, gender, country, zipcode, passportId));
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

            if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || country.isEmpty() || zipcode.isEmpty() || passportId.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            // Save guest to database
            boolean isSaved = guestBO.addGuest(new GuestDTO(userId, id, name, gender, country, zipcode, passportId));
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

    public void CodeSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            GuestDTO guestDTO = (GuestDTO) guestBO.searchGuest(txtId.getText());
            if (guestDTO != null) {
                txtUser.setText(guestDTO.getUserId());
                txtId.setText(guestDTO.getId());
                txtName.setText(guestDTO.getName());
                String gender = guestDTO.getGender();
                if (gender != null && gender.equals("Male")) {
                    checkMale.setSelected(true);
                } else if (gender != null && gender.equals("Female")) {
                    checkFemale.setSelected(true);
                }
                txtCountry.setText(guestDTO.getCountry());
                txtZipcode.setText(guestDTO.getZipCode());
                txtPassportId.setText(guestDTO.getPassportId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    private String generateNewGuestId() {
        try {
            return guestBO.generateNewGuestID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblGuest.getItems().isEmpty()) {
            return "G00-001";
        } else {
            String id = getLastGuestId();
            int newGuestId = Integer.parseInt(id.replace("G", "")) + 1;
            return String.format("G00-%03d", newGuestId);
        }
    }

    private String getLastGuestId() {
        List<GuestTM> tempGuestList = new ArrayList<>(tblGuest.getItems());
        Collections.sort(tempGuestList);
        return tempGuestList.get(tempGuestList.size() - 1).getId();
    }


    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtUser.clear();
        txtPassportId.clear();
        txtZipcode.clear();
        txtCountry.clear();
        checkFemale.setSelected(false);
        checkMale.setSelected(false);
        txtName.clear();
        txtId.setDisable(false);
        txtId.setEditable(false);
        txtId.setText(generateNewGuestId());
        txtUser.requestFocus();
        txtUser.setDisable(false);
        txtPassportId.setDisable(false);
        txtZipcode.setDisable(false);
        txtCountry.setDisable(false);
        checkMale.setDisable(false);
        checkFemale.setDisable(false);
        txtName.setDisable(false);
        btnUpdate.setDisable(false);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
        tblGuest.getSelectionModel().clearSelection();
    }
}
