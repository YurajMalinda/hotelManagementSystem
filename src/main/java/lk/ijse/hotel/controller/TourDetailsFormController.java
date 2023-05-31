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
import lk.ijse.hotel.dao.TourDAOImpl;
import lk.ijse.hotel.dao.TourDetailsDAOImpl;
import lk.ijse.hotel.tm.TourDetail;
import lk.ijse.hotel.tm.TourDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TourDetailsFormController {

    public AnchorPane tourDetailsPane;
    public TableView <TourDetailTM> tblTourDetails;
    public TableColumn colTourId;
    public TableColumn colBookingId;
    public TableColumn colAmount;
    public TableColumn colDate;
    public ComboBox cmbBookingId;
    public ComboBox cmbTourId;
    public TextField txtAmount;
    public DatePicker txtDate;

    public void initialize() {
        setCellValueFactory();
        getAll();
        loadBookingIds();
        loadTourIds();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblTourDetails.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbBookingId.setValue(newSelection.getBookingId());
                cmbTourId.setValue(newSelection.getTourId());
                txtAmount.setText(newSelection.getAmount());
                txtDate.setValue(LocalDate.parse(newSelection.getDate()));
            }
        });
    }

    private void loadBookingIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = BookingModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbBookingId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadTourIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = TourDAOImpl.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbTourId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colTourId.setCellValueFactory(new PropertyValueFactory<>("tourId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    private void getAll() {
        try {
            ObservableList<TourDetailTM> obList = FXCollections.observableArrayList();
            List<TourDetail> tourDetailsList = TourDetailsDAOImpl.getAll();

            for (TourDetail tourDetail : tourDetailsList) {
                obList.add(new TourDetailTM(
                        tourDetail.getBookingId(),
                        tourDetail.getTourId(),
                        tourDetail.getAmount(),
                        tourDetail.getDate()
                ));
            }
            tblTourDetails.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1) {
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) tourDetailsPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) tourDetailsPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = (String) cmbBookingId.getValue();
        try {
            boolean isDeleted = TourDetailsDAOImpl.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String bookingId = (String) cmbBookingId.getValue();
            String tourId = (String) cmbTourId.getValue();
            String amount = txtAmount.getText();
            LocalDate selectedDate = txtDate.getValue();
            String date=selectedDate.toString();

            // Validate fields
            validateFields(bookingId, tourId, amount, date);

            TourDetail tourDetail= new TourDetail(bookingId,tourId, amount, date);
            boolean isUpdated = TourDetailsDAOImpl.update(tourDetail);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Tour Details updated!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        try {
            String bookingId = (String) cmbBookingId.getValue();
            String tourId = (String) cmbTourId.getValue();
            String amount = txtAmount.getText();
            LocalDate selectedDate = txtDate.getValue();
            String date=selectedDate.toString();

            // Validate fields
            validateFields(bookingId, tourId, amount, date);

            TourDetail tourDetail= new TourDetail(bookingId,tourId, amount, date);
            boolean isSaved = TourDetailsDAOImpl.add(tourDetail);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Tour Details saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void validateFields(String bookingId, String tourId, String amount, String date) {
        // Check if all the required fields are not empty
        if (bookingId.isEmpty() || tourId.isEmpty() || amount.isEmpty() || date.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        cmbTourId.setValue(null);
        cmbBookingId.setValue(null);
        txtDate.setValue(null);
        txtAmount.clear();
    }


}
