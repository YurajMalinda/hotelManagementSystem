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
import lk.ijse.hotel.dao.InventoryDAOImpl;
import lk.ijse.hotel.dto.InventoryDTO;
import lk.ijse.hotel.view.tdm.InventoryTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InventoryFormController {
    public TextField txtId;
    public TextArea txtDetails;
    public TextField txtName;
    public TextField txtPrice;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colPrice;
    public TableView <InventoryTM> tblInventory;
    @FXML
    private AnchorPane inventoryPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblInventory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            ObservableList<InventoryTM> obList = FXCollections.observableArrayList();
            List<InventoryDTO> inventoryDTOList = InventoryDAOImpl.getAll();

            for (InventoryDTO inventoryDTO : inventoryDTOList) {
                obList.add(new InventoryTM(
                        inventoryDTO.getId(),
                        inventoryDTO.getName(),
                        inventoryDTO.getDetails(),
                        inventoryDTO.getPrice()
                ));
            }
            tblInventory.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) inventoryPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) inventoryPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = InventoryDAOImpl.delete(id);
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
            boolean isUpdated = InventoryDAOImpl.update(id, name, details, price);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
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

            // Validate Inventory ID
            validateInventoryId(id);

            validatePrice(price);

            InventoryDTO inventoryDTO = new InventoryDTO(id, name, details, price);
            boolean isSaved = InventoryDAOImpl.add(inventoryDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Item to database!").show();
        }
    }

    public void validateFields(String id, String name, String details) {
        // Check if all the required fields are not empty
        if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateInventoryId(String id) {
        // Check if Inventory ID is valid using a regular expression
        String inventoryIdRegex = "^Item\\d+$";
        if (!id.matches(inventoryIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid item ID starting with 'Item' followed by one or more digits (e.g. Item1)!");
        }
    }

    public static boolean validatePrice(Double price) {
        if (price == null || price.isNaN()) {
            System.out.println("The price is empty.");
            return false;
        } else {
            System.out.println("The price is not empty.");
            return true;
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
            InventoryDTO inventoryDTO = InventoryDAOImpl.search(txtId.getText());
            if (inventoryDTO != null) {
                txtId.setText(inventoryDTO.getId());
                txtName.setText(inventoryDTO.getName());
                txtDetails.setText(inventoryDTO.getDetails());
                txtPrice.setText(String.valueOf(inventoryDTO.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
