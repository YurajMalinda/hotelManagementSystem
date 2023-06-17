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
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.InventoryBO;
import lk.ijse.hotel.dto.InventoryDTO;
import lk.ijse.hotel.dto.tdm.InventoryTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    @FXML
    private AnchorPane inventoryPane;
    
    InventoryBO inventoryBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.INVENTORY_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
        txtId.setDisable(true);
        txtId.setEditable(false);
        txtPrice.setDisable(true);
        txtDetails.setDisable(true);
        txtName.setDisable(true);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        txtPrice.setOnAction(event -> btnAdd.fire());
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
            List<InventoryDTO> inventoryDTOList = inventoryBO.getAllItems();

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
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) inventoryPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) inventoryPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = inventoryBO.deleteItem(id);
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
            boolean isUpdated = inventoryBO.updateItem(new InventoryDTO(id, name, details, price));
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

            if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            validatePrice(price);

            boolean isSaved = inventoryBO.addItem(new InventoryDTO(id, name, details, price));
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

    public static boolean validatePrice(Double price) {
        if (price == null || price.isNaN()) {
            System.out.println("The price is empty.");
            return false;
        } else {
            System.out.println("The price is not empty.");
            return true;
        }
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            InventoryDTO inventoryDTO = inventoryBO.searchItem(txtId.getText());
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

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
        txtId.setDisable(false);
        txtId.setEditable(false);
        txtId.setText(generateNewItemId());
        txtName.requestFocus();
        txtPrice.setDisable(false);
        txtDetails.setDisable(false);
        txtName.setDisable(false);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        tblInventory.getSelectionModel().clearSelection();
    }

    private String generateNewItemId() {
        try {
            return inventoryBO.generateNewItemID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblInventory.getItems().isEmpty()) {
            return "I00-001";
        } else {
            String id = getLastItemId();
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I00-%03d", newItemId);
        }
    }

    private String getLastItemId() {
        List<InventoryTM> tempItemList = new ArrayList<>(tblInventory.getItems());
        Collections.sort(tempItemList);
        return tempItemList.get(tempItemList.size() - 1).getId();
    }
}
