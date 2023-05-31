package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.hotel.dao.custom.impl.BookingDAOImpl;
import lk.ijse.hotel.dao.custom.impl.GuestDAOImpl;
import lk.ijse.hotel.dto.BookingDTO;
import lk.ijse.hotel.view.tdm.BookingTM;
import lk.ijse.hotel.dao.custom.impl.RoomDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class BookingFormController {

    public TextField txtBookingId;
    public TableView<BookingTM> tblBooking;
    public TableColumn colGuestId;
    public TableColumn colCheckIn;
    public TableColumn colBookingId;
    public TableColumn colDate;
    public TableColumn colRoomId;
    public TableColumn colCheckOut;
    public TableColumn colDetails;
    public JFXComboBox cmbGuestId;
    public JFXComboBox cmbRoomId;
    public DatePicker txtDate;
    public DatePicker txtCheckIn;
    public DatePicker txtCheckOut;

    @FXML
    private AnchorPane bookingPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        loadGuestIds();
        loadRoomIds();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblBooking.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbGuestId.setValue(newSelection.getGuestId());
                txtBookingId.setText(newSelection.getBookingId());
                txtDate.setValue(LocalDate.parse(newSelection.getBookingDate()));
                cmbRoomId.setValue(newSelection.getRoomId());
                txtCheckIn.setValue(LocalDate.parse(newSelection.getCheckIn()));
                txtCheckOut.setValue(LocalDate.parse(newSelection.getCheckOut()));
            }
        });
    }

    private void loadGuestIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = GuestDAOImpl.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbGuestId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadRoomIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = RoomDAOImpl.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbRoomId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
    }

    @FXML
    private void getAll() {
        try {
            ObservableList<BookingTM> obList = FXCollections.observableArrayList();
            List<BookingDTO> bookingDTOList = BookingDAOImpl.getAll();

            for (BookingDTO bookingDTO : bookingDTOList) {
                obList.add(new BookingTM(
                        bookingDTO.getGuestId(),
                        bookingDTO.getBookingId(),
                        bookingDTO.getBookingDate(),
                        bookingDTO.getRoomId(),
                        bookingDTO.getCheckIn(),
                        bookingDTO.getCheckOut()
                ));
            }
            tblBooking.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1) {
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) bookingPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) bookingPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtBookingId.getText();
        try {
            boolean isDeleted = BookingDAOImpl.delete(id);
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
            String guestId = (String) cmbGuestId.getValue();
            String bookingId = txtBookingId.getText();
            String roomId = (String) cmbRoomId.getValue();
            LocalDate selectedOutDate = txtCheckOut.getValue();
            String checkOut = selectedOutDate.toString();

            // Validate booking ID
            validateBookingId(bookingId);

            boolean isUpdated = BookingDAOImpl.update(guestId, bookingId, roomId, checkOut);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking updated!").show();
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
            String guestId = (String) cmbGuestId.getValue();
            String bookingId = txtBookingId.getText();
            LocalDate selectedBookingDate = txtDate.getValue();
            String bookingDate = selectedBookingDate.toString();
            String roomId = (String) cmbRoomId.getValue();
            LocalDate selectedInDate = txtCheckIn.getValue();
            String checkIn = selectedInDate.toString();
            LocalDate selectedOutDate = txtCheckOut.getValue();
            String checkOut = selectedOutDate.toString();

            // Validate fields
            validateFields(guestId, bookingId, bookingDate, roomId, checkIn, checkOut);

            // Validate booking ID
            validateBookingId(bookingId);

            BookingDTO bookingDTO = new BookingDTO(guestId, bookingId, bookingDate, roomId, checkIn, checkOut);

            // Save booking to database
            boolean isSaved = BookingDAOImpl.add(bookingDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking saved!").show();
                BookingDAOImpl.releaseRoom(roomId);
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }
    }

    public void validateFields(String guestId, String bookingId, String bookingDate, String roomId, String checkIn, String checkOut) {
        // Check if all the required fields are not empty
        if (guestId.isEmpty() || bookingId.isEmpty() || bookingDate.isEmpty() || roomId.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateBookingId(String id) {
        // Check if booking ID is valid using a regular expression
        String bookingIdRegex = "^B\\d+$";
        if (!id.matches(bookingIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid booking ID starting with 'B' followed by one or more digits (e.g. B123)!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        cmbGuestId.setValue(null);
        txtBookingId.clear();
        txtDate.setValue(null);
        cmbRoomId.setValue(null);
        txtCheckIn.setValue(null);
        txtCheckOut.setValue(null);
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            BookingDTO bookingDTO = BookingDAOImpl.search(txtBookingId.getText());
            if (bookingDTO != null) {
                cmbGuestId.setValue(bookingDTO.getGuestId());
                txtBookingId.setText(bookingDTO.getBookingId());
                txtDate.setValue(LocalDate.parse(bookingDTO.getBookingDate()));
                cmbRoomId.setValue(bookingDTO.getRoomId());
                txtCheckIn.setValue(LocalDate.parse(bookingDTO.getCheckIn()));
                txtCheckOut.setValue(LocalDate.parse(bookingDTO.getCheckOut()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
