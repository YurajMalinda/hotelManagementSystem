package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.BookingBO;
import lk.ijse.hotel.bo.custom.GuestBO;
import lk.ijse.hotel.bo.custom.RoomBO;
import lk.ijse.hotel.dto.BookingDTO;
import lk.ijse.hotel.dto.GuestDTO;
import lk.ijse.hotel.dto.RoomDTO;
import lk.ijse.hotel.view.tdm.BookingTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAddNew;

    @FXML
    private AnchorPane bookingPane;

    BookingBO bookingBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKING_BO);
    RoomBO roomBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ROOM_BO);
    GuestBO guestBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.GUEST_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        loadGuestIds();
        loadRoomIds();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        cmbGuestId.setValue(null);
        txtBookingId.clear();
        txtDate.setValue(null);
        cmbRoomId.setValue(null);
        txtCheckIn.setValue(null);
        txtCheckOut.setValue(null);
        cmbGuestId.setDisable(true);
        txtBookingId.setDisable(true);
        txtDate.setDisable(true);
        cmbRoomId.setDisable(true);
        txtCheckIn.setDisable(true);
        txtCheckOut.setDisable(true);
        txtBookingId.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        txtCheckOut.setOnAction(event -> btnAdd.fire());
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
            ArrayList<GuestDTO> allGuests = guestBO.getAllGuests();
            for (GuestDTO c : allGuests) {
                cmbGuestId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load booking ids").show();
        }
    }

    private void loadRoomIds() {
        try {
            ArrayList<RoomDTO> allRooms = roomBO.getAllRooms();
            for (RoomDTO c : allRooms) {
                cmbRoomId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load booking ids").show();
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
            List<BookingDTO> bookingDTOList = bookingBO.getAllBookings();

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

            if (guestId.isEmpty() || bookingId.isEmpty() || bookingDate.isEmpty() || roomId.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = bookingBO.addBooking(new BookingDTO(guestId, bookingId, bookingDate, roomId, checkIn, checkOut));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking saved!").show();
                bookingBO.releaseRoom(roomId);
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }
        btnAddNew.fire();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String guestId = (String) cmbGuestId.getValue();
            String bookingId = txtBookingId.getText();
            String roomId = (String) cmbRoomId.getValue();
            LocalDate selectedOutDate = txtCheckOut.getValue();
            String checkOut = selectedOutDate.toString();

            if (guestId.isEmpty() || bookingId.isEmpty() || roomId.isEmpty() || checkOut.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isUpdated = bookingBO.updateBooking(new BookingDTO(guestId, roomId, checkOut, bookingId));
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
        btnAddNew.fire();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtBookingId.getText();
        try {
            boolean isDeleted = bookingBO.deleteBooking(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                        getAll();
                        initUI();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            BookingDTO bookingDTO = (BookingDTO) bookingBO.searchBooking(txtBookingId.getText());
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

    private String generateNewBookingId() {
        try {
            return bookingBO.generateNewBookingID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblBooking.getItems().isEmpty()) {
            return "B00-001";
        } else {
            String id = getLastBookingId();
            int newBookingId = Integer.parseInt(id.replace("B", "")) + 1;
            return String.format("B00-%03d", newBookingId);
        }
    }

    private String getLastBookingId() {
        List<BookingTM> tempBookingList = new ArrayList<>(tblBooking.getItems());
        Collections.sort(tempBookingList);
        return tempBookingList.get(tempBookingList.size() - 1).getBookingId();
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        cmbGuestId.setValue(null);
        txtBookingId.clear();
        txtDate.setValue(null);
        cmbRoomId.setValue(null);
        txtCheckIn.setValue(null);
        txtCheckOut.setValue(null);
        cmbGuestId.setDisable(true);
        cmbGuestId.requestFocus();
        txtBookingId.setDisable(false);
        txtBookingId.setText(generateNewBookingId());
        txtDate.setDisable(false);
        cmbRoomId.setDisable(false);
        txtCheckIn.setDisable(false);
        txtCheckOut.setDisable(false);
        txtBookingId.setEditable(false);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        tblBooking.getSelectionModel().clearSelection();
    }
}
