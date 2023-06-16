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
import lk.ijse.hotel.bo.custom.SupplierBO;
import lk.ijse.hotel.dto.SupplierDTO;
import lk.ijse.hotel.view.tdm.SupplierTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplierFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TableColumn<Object, Object> colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colContact;
    public TableView<SupplierTM> tblSupplier;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    public TextField txtDetails;
    @FXML
    private AnchorPane supplierPane;
    
    SupplierBO supplierBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SUPPLIER_BO);

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
        initUI();
    }

    private void initUI() {
        txtId.clear();
        txtDetails.clear();
        txtContact.clear();
        txtName.clear();
        txtId.setDisable(true);
        txtDetails.setDisable(true);
        txtName.setDisable(true);
        txtContact.setDisable(true);
        txtId.setEditable(false);
        txtDetails.setOnAction(event -> btnAdd.fire());
    }

    private void setSelectToTxt() {
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtContact.setText(newSelection.getContact());
                txtDetails.setText(newSelection.getDetails());
            }
        });
    }


    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
    }

    private void getAll () {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<SupplierDTO> supplierDTOList = supplierBO.getAllSuppliers();

            for (SupplierDTO supplierDTO : supplierDTOList) {
                obList.add(new SupplierTM(
                        supplierDTO.getId(),
                        supplierDTO.getName(),
                        supplierDTO.getContact(),
                        supplierDTO.getDetails()
                ));
            }
           tblSupplier.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) supplierPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) supplierPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = supplierBO.deleteSupplier(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String contact =txtContact.getText();
            String details = txtDetails.getText();

            if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isUpdated = supplierBO.updateSupplier(new SupplierDTO(id, name, contact, details));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String contact =txtContact.getText();
            String details = txtDetails.getText();

            if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = supplierBO.addSupplier(new SupplierDTO(id, name, contact, details));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            SupplierDTO supplierDTO = supplierBO.searchSupplier(txtId.getText());
            if (supplierDTO != null) {
                txtId.setText(supplierDTO.getId());
                txtName.setText(supplierDTO.getName());
                txtContact.setText(supplierDTO.getContact());
                txtDetails.setText(supplierDTO.getDetails());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtDetails.clear();
        txtId.setDisable(false);
        txtId.setText(generateNewSupplierId());
        txtName.requestFocus();
        txtDetails.setDisable(false);
        txtName.setDisable(false);
        txtContact.setDisable(false);
        txtId.setEditable(false);
        tblSupplier.getSelectionModel().clearSelection();
    }

    private String generateNewSupplierId() {
        try {
            return supplierBO.generateNewSupplierID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblSupplier.getItems().isEmpty()) {
            return "SP0-001";
        } else {
            String id = getLastSupplierId();
            int newSupId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("SP0-%03d", newSupId);
        }
    }

    private String getLastSupplierId() {
        List<SupplierTM> tempSupList = new ArrayList<>(tblSupplier.getItems());
        Collections.sort(tempSupList);
        return tempSupList.get(tempSupList.size() - 1).getId();
    }
}
