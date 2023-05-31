package lk.ijse.hotel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hotel.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportFormController {
    public AnchorPane reportPane;
    public LineChart monthlyChart;

    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Monthly income");

        try {
            // connect to the database
            Connection conn = DBConnection.getInstance().getConnection();

            // retrieve the payment data for each month
            PreparedStatement stmt = conn.prepareStatement("SELECT SUM(totalPrice) as total FROM payment WHERE checkOutDate BETWEEN ? AND ?");
            for (int month = 1; month <= 12; month++) {
                LocalDate start = LocalDate.of(2023, month, 1);
                LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
                stmt.setDate(1, java.sql.Date.valueOf(start));
                stmt.setDate(2, java.sql.Date.valueOf(end));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Double totalAmount = rs.getDouble("total");
                    series.getData().add(new XYChart.Data<>(Month.of(month).toString(), totalAmount));
                } else {
                    series.getData().add(new XYChart.Data<>(Month.of(month).toString(), 0.0));
                }
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        monthlyChart.getData().add(series);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) reportPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) reportPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }
}
