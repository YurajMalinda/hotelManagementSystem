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
import lk.ijse.hotel.bo.custom.BookingBO;
import lk.ijse.hotel.bo.custom.TourBO;
import lk.ijse.hotel.bo.custom.TourDetailsBO;
import lk.ijse.hotel.dto.BookingDTO;
import lk.ijse.hotel.dto.TourDTO;
import lk.ijse.hotel.dto.TourDetailDTO;
import lk.ijse.hotel.view.tdm.TourDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    
    TourDetailsBO tourDetailsBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TOUR_DETAILS_BO);
    BookingBO bookingBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKING_BO);
    TourBO tourBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TOUR_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        loadBookingIds();
        loadTourIds();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        cmbTourId.setValue(null);
        cmbBookingId.setValue(null);
        txtDate.setValue(null);
        txtAmount.clear();
        cmbTourId.setDisable(true);
        cmbBookingId.setDisable(true);
        txtDate.setDisable(true);
        txtAmount.setDisable(true);
        txtDate.setOnAction(event -> btnAdd.fire());
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
            ArrayList<BookingDTO> allBookings = bookingBO.getAllBookings();
            for (BookingDTO c : allBookings) {
                cmbBookingId.getItems().add(c.getBookingId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load food ids").show();
        }
    }

    private void loadTourIds() {
        try {
            ArrayList<TourDTO> allTours = tourBO.getAllTours();
            for (TourDTO c : allTours) {
                cmbTourId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load food ids").show();
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
            List<TourDetailDTO> tourDetailsList = tourDetailsBO.getAllTourDetails();

            for (TourDetailDTO tourDetail : tourDetailsList) {
                obList.add(new TourDetailTM(tourDetail.getBookingId(), tourDetail.getTourId(), tourDetail.getAmount(), tourDetail.getDate()));
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
            boolean isDeleted = tourDetailsBO.deleteTourDetail(id);
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

            if (bookingId.isEmpty() || tourId.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }
            boolean isUpdated = tourDetailsBO.updateTourDetail(new TourDetailDTO(bookingId,tourId, amount, date));
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

            if (bookingId.isEmpty() || tourId.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = tourDetailsBO.addTourDetail(new TourDetailDTO(bookingId,tourId, amount, date));
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

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        cmbTourId.setValue(null);
        cmbBookingId.setValue(null);
        txtDate.setValue(null);
        txtAmount.clear();
        cmbTourId.setDisable(false);
        cmbBookingId.setDisable(false);
        txtDate.setDisable(false);
        txtAmount.setDisable(false);
        cmbBookingId.requestFocus();
        tblTourDetails.getSelectionModel().clearSelection();
    }
}
