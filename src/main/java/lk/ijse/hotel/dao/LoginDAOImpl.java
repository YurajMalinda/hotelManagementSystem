package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl {
    public static List<String> loadTitles() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT title FROM user");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
        /*if (rst.next()) {
            // Login successful, load dashboard
            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
            stage.setTitle("Manage");
            stage.centerOnScreen();
            stage.show();

        }*/
}
