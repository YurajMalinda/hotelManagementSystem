package lk.ijse.hotel.controller;

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
import lk.ijse.hotel.dao.EmployeeDAOImpl;
import lk.ijse.hotel.dto.EmployeeDTO;
import lk.ijse.hotel.tm.EmployeeTM;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {
    public TextField txtId;
    public TextField txtGender;
    public TextField txtAddress;
    public TextField txtNic;
    public TextField txtName;
    public TextField txtUserId;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colGender;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colNic;
    public TableColumn colUserId;
    public TextField txtEmail;
    public TableView <EmployeeTM> tblEmployee;
    public CheckBox chkMale;
    public CheckBox chkFemale;
    @FXML
    private AnchorPane employeePane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtUserId.setText(newSelection.getId());
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtGender.setText(newSelection.getGender());
                txtEmail.setText(newSelection.getEmail());
                txtNic.setText(newSelection.getNic());
                txtAddress.setText(newSelection.getAddress());
            }
        });
    }

    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<EmployeeDTO> employeeDTOList = EmployeeDAOImpl.getAll();

            for (EmployeeDTO employeeDTO : employeeDTOList) {
                obList.add(new EmployeeTM(
                        employeeDTO.getUserId(),
                        employeeDTO.getId(),
                        employeeDTO.getName(),
                        employeeDTO.getGender(),
                        employeeDTO.getEmail(),
                        employeeDTO.getNic(),
                        employeeDTO.getAddress()
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1) {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) employeePane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) employeePane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = EmployeeDAOImpl.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();
        String id = txtId.getText();
        String name = txtName.getText();
        String gender = "";
        if (chkMale.isSelected()) {
            gender = "Male";
            chkFemale.setSelected(false);
        } else if (chkFemale.isSelected()) {
            gender = "Female";
            chkMale.setSelected(false);
        }
        String email = txtEmail.getText();
        String nic = txtNic.getText();
        String address = txtAddress.getText();

        try {
            boolean isUpdated = EmployeeDAOImpl.update(id, name, gender, address, email, nic);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                getAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            String userId = txtUserId.getText();
            String id = txtId.getText();
            String name = txtName.getText();
            String gender = "";
            if (chkMale.isSelected()) {
                gender = "Male";
                chkFemale.setSelected(false);
            } else if (chkFemale.isSelected()) {
                gender = "Female";
                chkMale.setSelected(false);
            }
            String email = txtEmail.getText();
            String nic = txtNic.getText();
            String address = txtAddress.getText();

            // Validate fields
            validateFields(userId, id, name, gender, email, nic, address);

            // Validate employee ID
            validateEmployeeId(id);

            // Validate email address
            validateEmail(email);

            EmployeeDTO employeeDTO = new EmployeeDTO(id, name, gender, email, nic, address, userId);

            // Save employee to database
            boolean isSaved = EmployeeDAOImpl.add(employeeDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }
    }

    public void validateFields(String userId, String id, String name, String gender, String email, String nic, String address) {
        // Check if all the required fields are not empty
        if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() || nic.isEmpty() || address.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateEmployeeId(String id) {
        // Check if employee ID is valid using a regular expression
        String employeeIdRegex = "^E\\d+$";
        if (!id.matches(employeeIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid employee ID starting with 'E' followed by one or more digits (e.g. E123)!");
        }
    }

    public void validateEmail(String email) {
        // Check if the email address is valid using a regular expression
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Please enter a valid email address!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtNic.clear();
        txtAddress.clear();
        chkFemale.setSelected(false);
        chkMale.setSelected(false);
        txtUserId.clear();
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            EmployeeDTO employeeDTO = EmployeeDAOImpl.search(txtId.getText());
            if (employeeDTO != null) {
                txtId.setText(employeeDTO.getId());
                txtName.setText(employeeDTO.getName());
                String gender = employeeDTO.getGender();
                if (gender != null && gender.equals("Male")) {
                    chkMale.setSelected(true);
                } else if (gender != null && gender.equals("Female")) {
                    chkFemale.setSelected(true);
                }
                txtEmail.setText(employeeDTO.getEmail());
                txtNic.setText(employeeDTO.getNic());
                txtAddress.setText(employeeDTO.getAddress());
                txtUserId.setText(employeeDTO.getUserId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
