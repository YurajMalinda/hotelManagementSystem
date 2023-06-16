package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.UserBO;
import lk.ijse.hotel.dto.UserDTO;
import lk.ijse.hotel.view.tdm.UserTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtPassword;
    public TableView<UserTM> tblUser;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colPassword;
    public AnchorPane userPane;
    public TextField txtTitle;
    public TableColumn colTitle;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;

    UserBO userBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtPassword.clear();
        txtTitle.clear();
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtPassword.setDisable(true);
        txtTitle.setDisable(true);
        txtTitle.setOnAction(event -> btnAdd.fire());
    }

    private void setSelectToTxt() {
        tblUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtPassword.setText(newSelection.getPassword());
                txtTitle.setText(newSelection.getTitle());
            }
        });
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    private void getAll() {
        try {
            ObservableList<UserTM> obList = FXCollections.observableArrayList();
            List<UserDTO> userDTOList = userBO.getAllUsers();

            for (UserDTO userDTO : userDTOList) {
                obList.add(new UserTM(
                        userDTO.getId(),
                        userDTO.getName(),
                        userDTO.getPassword(),
                        userDTO.getTitle()
                ));
            }
            tblUser.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) userPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) userPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = userBO.deleteUser(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String password =txtPassword.getText();
        String title = txtTitle.getText();

        validateFields(id, name, password, title);

        try {
            boolean isUpdated = userBO.updateUser(new UserDTO(id, name, password, title));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated!").show();
                getAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String password =txtPassword.getText();
            String title = txtTitle.getText();

            // Validate fields
            validateFields(id, name, password, title);

            // Validate user ID
            validateUserId(id);

            boolean isSaved = userBO.addUser(new UserDTO(id, name, password, title));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void validateFields(String id, String name, String password, String title) {
        // Check if all the required fields are not empty
        if (id.isEmpty() || name.isEmpty() || password.isEmpty() || title.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateUserId(String id) {
        // Check if booking ID is valid using a regular expression
        String scheduleIdRegex = "^U\\d+$";
        if (!id.matches(scheduleIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid schedule ID starting with 'S' followed by one or more digits (e.g. S123)!");
        }
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            UserDTO userDTO = userBO.searchUser(txtId.getText());
            if (userDTO != null) {
                txtId.setText(userDTO.getId());
                txtName.setText(userDTO.getName());
                txtPassword.setText(userDTO.getPassword());
                txtTitle.setText(userDTO.getTitle());
            }else{
                new Alert(Alert.AlertType.ERROR, "user not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtPassword.clear();
        txtTitle.clear();
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtPassword.setDisable(false);
        txtTitle.setDisable(false);
        txtId.requestFocus();
        tblUser.getSelectionModel().clearSelection();
    }
}
