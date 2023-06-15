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
import lk.ijse.hotel.dao.CrudDAO;
import lk.ijse.hotel.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.hotel.dto.EmployeeDTO;
import lk.ijse.hotel.view.tdm.BookingTM;
import lk.ijse.hotel.view.tdm.EmployeeTM;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    @FXML
    private AnchorPane employeePane;

    CrudDAO crudDAO = new EmployeeDAOImpl();

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtNic.clear();
        txtAddress.clear();
        chkFemale.setSelected(false);
        chkMale.setSelected(false);
        txtUserId.clear();
        txtId.setEditable(false);
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtEmail.setDisable(true);
        txtNic.setDisable(true);
        txtAddress.setDisable(true);
        chkMale.setDisable(true);
        chkFemale.setDisable(true);
        txtUserId.setDisable(true);
        txtNic.setOnAction(event -> btnAdd.fire());
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
            List<EmployeeDTO> employeeDTOList = crudDAO.getAll();

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
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) employeePane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) employeePane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = crudDAO.delete(id);
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

        if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() || nic.isEmpty() || address.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = crudDAO.update(new EmployeeDTO(id, name, gender, address, email, nic));
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

            if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() || nic.isEmpty() || address.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }
            // Validate email address
            validateEmail(email);

            // Save employee to database
            boolean isSaved = crudDAO.add(new EmployeeDTO(id, name, gender, email, nic, address, userId));
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

    public void validateEmail(String email) {
        // Check if the email address is valid using a regular expression
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Please enter a valid email address!");
        }
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            EmployeeDTO employeeDTO = (EmployeeDTO) crudDAO.search(txtId.getText());
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

    private String generateNewEmployeeId() {
        try {
            return crudDAO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblEmployee.getItems().isEmpty()) {
            return "E00-001";
        } else {
            String id = getLastEmployeeId();
            int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E00-%03d", newEmployeeId);
        }
    }

    private String getLastEmployeeId() {
        List<EmployeeTM> tempBookingList = new ArrayList<>(tblEmployee.getItems());
        Collections.sort(tempBookingList);
        return tempBookingList.get(tempBookingList.size() - 1).getId();
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtNic.clear();
        txtAddress.clear();
        chkFemale.setSelected(false);
        chkMale.setSelected(false);
        txtUserId.clear();
        txtId.setEditable(false);
        txtId.setText(generateNewEmployeeId());
        txtUserId.requestFocus();
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtEmail.setDisable(false);
        txtNic.setDisable(false);
        txtAddress.setDisable(false);
        chkMale.setDisable(false);
        chkFemale.setDisable(false);
        txtUserId.setDisable(false);
        tblEmployee.getSelectionModel().clearSelection();
    }
}
