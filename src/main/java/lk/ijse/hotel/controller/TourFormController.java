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
import lk.ijse.hotel.bo.custom.TourBO;
import lk.ijse.hotel.dto.TourDTO;
import lk.ijse.hotel.view.tdm.TourTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TourFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtPrice;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colPrice;
    public TableView<TourTM> tblTour;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    public TextField txtDetails;
    @FXML
    private AnchorPane tourPane;
    
    TourBO tourBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TOUR_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
        txtId.setDisable(true);
        txtPrice.setDisable(true);
        txtDetails.setDisable(true);
        txtName.setDisable(true);
        txtId.setEditable(false);
        txtPrice.setOnAction(event -> btnAdd.fire());
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
            List<TourDTO> tourDTOList = tourBO.getAllTours();

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
            boolean isDeleted = tourBO.deleteTour(id);
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

        if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = tourBO.updateTour(new TourDTO(id, name, details, price));
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

            if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = tourBO.addTour(new TourDTO(id, name, details, price));
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

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            TourDTO tourDTO = tourBO.searchTour(txtId.getText());
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

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
        txtId.setDisable(false);
        txtId.setText(generateNewTourId());
        txtName.requestFocus();
        txtPrice.setDisable(false);
        txtDetails.setDisable(false);
        txtName.setDisable(false);
        txtId.setEditable(false);
        tblTour.getSelectionModel().clearSelection();
    }

    private String generateNewTourId() {
        try {
            return tourBO.generateNewTourID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblTour.getItems().isEmpty()) {
            return "T00-001";
        } else {
            String id = getLastTourId();
            int newTourId = Integer.parseInt(id.replace("T", "")) + 1;
            return String.format("T00-%03d", newTourId);
        }
    }

    private String getLastTourId() {
        List<TourTM> tempTourList = new ArrayList<>(tblTour.getItems());
        Collections.sort(tempTourList);
        return tempTourList.get(tempTourList.size() - 1).getId();
    }
}
