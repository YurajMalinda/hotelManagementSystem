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
import lk.ijse.hotel.bo.custom.GuestBO;
import lk.ijse.hotel.bo.custom.PaymentBO;
import lk.ijse.hotel.bo.custom.RoomBO;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.*;
import lk.ijse.hotel.dto.tdm.PaymentTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentFormController {
    public AnchorPane paymentPane;
    public TableView <PaymentTM>tblPayment;
    public TableColumn colPaymentId;
    public TableColumn colGuestId;
    public TableColumn colName;
    public TableColumn colBookingId;
    public TableColumn colRoomId;
    public TableColumn colCheckIn;
    public TableColumn colCheckOut;
    public TableColumn colAmount;
    public TableColumn colTotal;

    @FXML
    public TextField txtPaymentId;
    @FXML
    public Label lblGuestId;
    @FXML
    public Label lblName;
    @FXML
    public TextField txtBookingId;
    @FXML
    public Label lblRoomId;
    @FXML
    public Label lblAmount;
    @FXML
    public Label lblPrice;
    @FXML
    public TextField txtSearch;
    @FXML
    public Label lblCheckIn;
    @FXML
    public Label lblCheckOut;
    public JFXButton btnAddNew;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    
    PaymentBO paymentBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PAYMENT_BO);
    BookingBO bookingBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKING_BO);
    RoomBO roomBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ROOM_BO);
    GuestBO guestBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.GUEST_BO);

    public void initialize() {
        getAll();
        setValueFactory();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtPaymentId.setText("");
        lblGuestId.setText("");
        lblName.setText("");
        txtBookingId.setText("");
        lblRoomId.setText("");
        lblCheckIn.setText("");
        lblCheckOut.setText("");
        lblAmount.setText("");
        lblPrice.setText("");
        txtPaymentId.setDisable(true);
        txtBookingId.setDisable(true);
        txtPaymentId.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void setSelectToTxt() {
        tblPayment.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtPaymentId.setText(newSelection.getPaymentId());
                lblGuestId.setText(newSelection.getGuestId());
                lblName.setText(newSelection.getGuestName());
                txtBookingId.setText(newSelection.getResId());
                lblRoomId.setText(newSelection.getRoomId());
                lblCheckIn.setText(newSelection.getCheckIn());
                lblCheckOut.setText(newSelection.getCheckOut());
                lblAmount.setText(String.valueOf(newSelection.getOrderAm()));
                lblPrice.setText(String.valueOf(newSelection.getTotal()));
            }
        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) paymentPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) paymentPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }
    @FXML
    private void setValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("orderAm"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }
    @FXML
    private void getAll() {
        try {
            ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
            List<PaymentDTO> paymentDTOList = paymentBO.getAllPayments();

            for (PaymentDTO paymentDTO : paymentDTOList) {
                obList.add(new PaymentTM(paymentDTO.getPaymentId(), paymentDTO.getGuestId(), paymentDTO.getGuestName(), paymentDTO.getResId(), paymentDTO.getRoomId(), paymentDTO.getCheckIn(), paymentDTO.getCheckOut(), paymentDTO.getOrderAm(), paymentDTO.getTotal()));
            }
            tblPayment.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    @FXML
    public void resOnAction(){
        String id = txtBookingId.getText();
        try {
            BookingDTO res = bookingBO.searchBooking(id);
            Double orderAm = getOrderAmmount(id);

            LocalDate checkIn = LocalDate.parse(res.getCheckIn());
            LocalDate checkOut = LocalDate.parse(res.getCheckOut());
            RoomDTO roomDTO = roomBO.searchRoom(res.getRoomId());
            Double price = Double.valueOf(roomDTO.getPrice());
            Double roomPrice = calculateRoomPrice(checkIn,checkOut,price);
            GuestDTO guestDTO = guestBO.searchGuest(res.getGuestId());
            Double finalAmmount = orderAm+roomPrice;
            fillResFields(res,orderAm,finalAmmount, guestDTO);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    public void fillResFields(BookingDTO res, Double orderAm, Double finalAmmount, GuestDTO guestDTO) {
        lblGuestId.setText(res.getGuestId());
        lblRoomId.setText(res.getRoomId());
        lblName.setText(guestDTO.getName());
        lblCheckIn.setText(res.getCheckIn());
        lblCheckOut.setText(res.getCheckOut());
        lblAmount.setText(String.valueOf(orderAm));
        lblPrice.setText(String.valueOf(finalAmmount));
    }

    @FXML
    public Double calculateRoomPrice(LocalDate checkIn, LocalDate checkOut, Double price) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        return price * days;
    }

    @FXML
    public Double getOrderAmmount(String id) {
        Double orderAmmount = 0.0;
        try {
            orderAmmount = paymentBO.getOrderAmount(id);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        return orderAmmount;
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        try {
            boolean isDeleted = paymentBO.deletePayment(paymentId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted!").show();
                clearTxt();
                getAll();
                setValueFactory();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Id Not Exist!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Please Check Details & Try Again!").show();
            clearTxt();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        String guestId = lblGuestId.getText();
        String guestName = lblName.getText();
        String reservationId = txtBookingId.getText();
        String roomId = lblRoomId.getText();
        String checkinDate = lblCheckIn.getText();
        String checkoutDate = lblCheckOut.getText();
        Double ordersAm = Double.valueOf(lblAmount.getText());
        Double totalPrice = Double.valueOf(lblPrice.getText());

        try {
            boolean isUpdated = paymentBO.updatePayment(new PaymentDTO(paymentId,guestId,guestName,reservationId,roomId,checkinDate,checkoutDate,ordersAm,totalPrice));
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
                getAll();
                setValueFactory();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Payment Id Not Exist!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Check Details & Try Again!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        String guestId = lblGuestId.getText();
        String guestName = lblName.getText();
        String reservationId = txtBookingId.getText();
        String roomId = lblRoomId.getText();
        String checkinDate = lblCheckIn.getText();
        String checkoutDate = lblCheckOut.getText();
        Double ordersAm = Double.valueOf(lblAmount.getText());
        Double totalPrice = Double.valueOf(lblPrice.getText());
        String release = "Available";

        try {
            boolean isSaved = paymentBO.addPayment(new PaymentDTO(paymentId,guestId,guestName,reservationId,roomId,checkinDate,checkoutDate,ordersAm,totalPrice));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!").show();
                roomBO.releaseRoom(roomId,release);
                getAll();
                setValueFactory();

                try {
                    JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Jasper/report.jrxml");
                    JRDesignQuery query=new JRDesignQuery();
                    query.setText("select payment.paymentId, payment.bookingId, payment.guestId, payment.guestName, payment.roomId, room.roomType, payment.checkInDate, payment.checkOutDate, payment.ordersAmount, payment.totalPrice FROM payment INNER JOIN guest ON payment.guestId=guest.guestId INNER JOIN booking ON payment.bookingId=booking.bookingId INNER JOIN room ON payment.roomId=room.roomId WHERE paymentId='"+txtPaymentId.getText()+"';");
                    jasperDesign.setQuery(query);

                    JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint,false);
                } catch ( SQLException | JRException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Payment Not saved ,Please Check Details & Try Again!").show();
            clearTxt();
        }
    }

    @FXML
    public void codeSearchOnAction(ActionEvent actionEvent) {
       resOnAction();
    }

    private void clearTxt(){
        txtPaymentId.setText("");
        lblGuestId.setText("");
        lblName.setText("");
        txtBookingId.setText("");
        lblRoomId.setText("");
        lblCheckIn.setText("");
        lblCheckOut.setText("");
        lblAmount.setText("");
        lblPrice.setText("");
    }

    private String generateNewPaymentId() {
        try {
            return paymentBO.generateNewPaymentID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblPayment.getItems().isEmpty()) {
            return "P00-001";
        } else {
            String id = getLastPaymentId();
            int newPaymentId = Integer.parseInt(id.replace("P", "")) + 1;
            return String.format("P00-%03d", newPaymentId);
        }
    }

    private String getLastPaymentId() {
        List<PaymentTM> tempPaymentList = new ArrayList<>(tblPayment.getItems());
        Collections.sort(tempPaymentList);
        return tempPaymentList.get(tempPaymentList.size() - 1).getPaymentId();
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtPaymentId.setText("");
        lblGuestId.setText("");
        lblName.setText("");
        txtBookingId.setText("");
        lblRoomId.setText("");
        lblCheckIn.setText("");
        lblCheckOut.setText("");
        lblAmount.setText("");
        lblPrice.setText("");
        txtPaymentId.setDisable(false);
        txtPaymentId.requestFocus();
        txtPaymentId.setText(generateNewPaymentId());
        txtBookingId.setDisable(false);
        txtPaymentId.setEditable(false);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        tblPayment.getSelectionModel().clearSelection();
    }
}
