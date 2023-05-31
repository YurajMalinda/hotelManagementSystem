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
import lk.ijse.hotel.dto.TourDTO;
import lk.ijse.hotel.view.tdm.TourTM;
import lk.ijse.hotel.dao.TourDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TourFormController {

    public TextField txtId;
    public TextField txtName;
    public TextArea txtDetails;
    public TextField txtPrice;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colPrice;
    public TableView<TourTM> tblTour;
    @FXML
    private AnchorPane tourPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblTour.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtDetails.setText(newSelection.getDetails());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void getAll() {
        try {
            ObservableList<TourTM> obList = FXCollections.observableArrayList();
            List<TourDTO> tourDTOList = TourDAOImpl.getAll();

            for (TourDTO tourDTO : tourDTOList) {
                obList.add(new TourTM(
                        tourDTO.getId(),
                        tourDTO.getName(),
                        tourDTO.getDetails(),
                        tourDTO.getPrice()
                ));
            }
            tblTour.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) tourPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) tourPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = TourDAOImpl.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String details =txtDetails.getText();
        Double price = Double.parseDouble(txtPrice.getText());

        try {
            boolean isUpdated = TourDAOImpl.update(id, name, details, price);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Tour updated!").show();
                getAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String details =txtDetails.getText();
            Double price = Double.parseDouble(txtPrice.getText());

            // Validate fields
            validateFields(id, name, details);

            // Validate Tour ID
            validateTourId(id);

            TourDTO tourDTO = new TourDTO(id, name, details, price);
            boolean isSaved = TourDAOImpl.add(tourDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Tour saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void validateFields(String id, String name, String details) {
        // Check if all the required fields are not empty
        if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateTourId(String id) {
        // Check if booking ID is valid using a regular expression
        String tourIdRegex = "^T\\d+$";
        if (!id.matches(tourIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid schedule ID starting with 'S' followed by one or more digits (e.g. S123)!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            TourDTO tourDTO = TourDAOImpl.search(txtId.getText());
            if (tourDTO != null) {
                txtId.setText(tourDTO.getId());
                txtName.setText(tourDTO.getName());
                txtDetails.setText(tourDTO.getDetails());
                txtPrice.setText(String.valueOf(tourDTO.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
