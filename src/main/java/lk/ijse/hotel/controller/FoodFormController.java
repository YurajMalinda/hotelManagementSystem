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
import lk.ijse.hotel.dao.FoodDAOImpl;
import lk.ijse.hotel.dto.FoodDTO;
import lk.ijse.hotel.tm.FoodTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FoodFormController {
    public TextField txtId;
    public TextArea txtDetails;
    public TextField txtName;
    public TextField txtPrice;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colPrice;
    public TableView <FoodTM> tblFood;
    @FXML
    private AnchorPane foodPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblFood.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtDetails.setText(newSelection.getDetails());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    private void getAll() {
        try {
            ObservableList<FoodTM> obList = FXCollections.observableArrayList();
            List<FoodDTO> foodDTOList = FoodDAOImpl.getAll();

            for (FoodDTO foodDTO : foodDTOList) {
                obList.add(new FoodTM(
                        foodDTO.getId(),
                        foodDTO.getName(),
                        foodDTO.getDetails(),
                        foodDTO.getPrice()
                ));
            }
            tblFood.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1) {
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) foodPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) foodPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = FoodDAOImpl.delete(id);
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
        String details =txtDetails.getText();
        Double price = Double.parseDouble(txtPrice.getText());

        try {
            boolean isUpdated = FoodDAOImpl.update(id, name, details, price);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Food updated!").show();
                getAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
            String id = txtId.getText();
            String name = txtName.getText();
            String details =txtDetails.getText();
            Double price = Double.parseDouble(txtPrice.getText());
    
            // Validate fields
            validateFields(id, name, details);
    
            // Validate food ID
            validateFoodId(id);
    
            FoodDTO foodDTO = new FoodDTO(id, name, details, price);
            boolean isSaved = FoodDAOImpl.add(foodDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Food saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Food to database!").show();
        }
    }

    public void validateFields(String id, String name, String details) {
        // Check if all the required fields are not empty
        if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateFoodId(String id) {
        // Check if employee ID is valid using a regular expression
        String employeeIdRegex = "^F\\d+$";
        if (!id.matches(employeeIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid food ID starting with 'F' followed by one or more digits (e.g. F123)!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            FoodDTO foodDTO = FoodDAOImpl.search(txtId.getText());
            if (foodDTO != null) {
                txtId.setText(foodDTO.getId());
                txtName.setText(foodDTO.getName());
                txtDetails.setText(foodDTO.getDetails());
                txtPrice.setText(String.valueOf(foodDTO.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
