package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.LoginBO;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.LoginDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class logInFormController {
    public TextField txtUserName;
    public TextField txtPassword;
    public ComboBox cmbTitle;
    public JFXButton btnLogin;
    @FXML
    private AnchorPane loginPane;

    LoginBO loginBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.LOGIN_BO);

    public void initialize() {
        loadTitles();
        initUI();
    }

    private void initUI() {
        txtPassword.setOnAction(event -> btnLogin.fire());
    }

    private void loadTitles() {
        try {
            ArrayList<LoginDTO> allLoginDetails = loginBO.getAllLogins();
            for (LoginDTO c : allLoginDetails) {
                cmbTitle.getItems().add(c.getTitle());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Titles").show();
        }
    }

    public void btnLoginOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        String title = (String) cmbTitle.getValue();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE title = ? AND userName = ? AND password = ?");
            stm.setString(1, title);
            stm.setString(2, userName);
            stm.setString(3, password);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                // Login successful, load appropriate form
                String path = "/view/";
                switch(title) {
                    case "Manager":
                        path += "dashboard_form.fxml";
                        break;
                    case "Receptionist":
                        path += "receptionist_form.fxml";
                        break;
                    // add more cases as needed for other user types
                }
                Stage stage = (Stage) loginPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(path))));
                stage.setTitle("Manage");
                stage.centerOnScreen();
                stage.show();
            } else {
                // Login failed, show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while trying to login!");
            alert.showAndWait();
        }
    }
}