package lk.ijse.hotel.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    public Label lblDate;
    public Label lblTime;
    @FXML
    private AnchorPane dashboardPane;
    @FXML

    private void initialize(){
        setLblDate();
        setLblTime();
    }
    public void btnGuestOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/guest_form.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Guest Manage");
        stage.centerOnScreen();
        BackButtonController.backButton=1;
    }

    @FXML
    public void btnRoomOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/room_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Room Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnBookingOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/booking_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Booking Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Payment Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnFoodsOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/food_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Food Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnInventoryOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/inventory_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Inventory Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/report_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Report Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Employee Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }
    @FXML
    public void btnTourOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/tour_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Tour Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Supplier Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }

    public void btnUserOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/user_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("User Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }

    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Order Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/log_in_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Log In");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }

    public void btnScheduleOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/schedule_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Schedule Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;
    }

    public void btnScheduleDetailOnAction(ActionEvent actionEvent) {
        BackButtonController.backButton=1;

    }

    public void btnTourDetailOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/tour_details_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setTitle("Tour Details Manage");
        stage.setScene(scene);
        BackButtonController.backButton=1;

    }

    public void setLblDate(){
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void setLblTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            lblTime.setText(LocalTime.now().format(formatter));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
