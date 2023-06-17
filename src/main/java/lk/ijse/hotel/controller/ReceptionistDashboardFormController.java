package lk.ijse.hotel.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReceptionistDashboardFormController implements Initializable {


    public AnchorPane receptionistPane;
    public Label lblDate;
    public Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLblTime();
        setLblDate();
    }

    public void btnGuestOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/guest_form.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Guest Manage");
        stage.centerOnScreen();
        BackButtonController.backButton = 0;
    }

    public void btnRoomOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/room_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Room Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnBookingOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/booking_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Booking Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Payment Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnFoodsOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/food_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Food Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Order Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/log_in_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Log In");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/report_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Report Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnTourOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/tour_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Tour Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
    }

    public void btnTourDetailOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/tour_details_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) receptionistPane.getScene().getWindow();
        stage.setTitle("Tour Manage");
        stage.setScene(scene);
        BackButtonController.backButton = 0;
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
