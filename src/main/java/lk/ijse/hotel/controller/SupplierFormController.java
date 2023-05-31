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
import lk.ijse.hotel.dto.SupplierDTO;
import lk.ijse.hotel.view.tdm.SupplierTM;
import lk.ijse.hotel.dao.SupplierDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TextArea txtDetails;
    public TableColumn<Object, Object> colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colContact;
    public TableView<SupplierTM> tblSupplier;
    @FXML
    private AnchorPane supplierPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
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
            List<SupplierDTO> supplierDTOList = SupplierDAOImpl.getAll();

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
            boolean isDeleted = SupplierDAOImpl.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                //        setCellValueFactory();
                //        getAll();
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

            // Validate fields
            validateFields(id, name, contact, details);

            // Validate Supplier ID
            validateSupplierId(id);

            SupplierDTO supplierDTO = new SupplierDTO(id, name, contact, details);

//            boolean isUpdated = SupplierModel.update(id, name, contact, details);
            boolean isUpdated = SupplierDAOImpl.update(supplierDTO);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
                //        setCellValueFactory();
                //        getAll();
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

            // Validate fields
            validateFields(id, name, contact, details);

            // Validate supplier ID
            validateSupplierId(id);

            SupplierDTO supplierDTO = new SupplierDTO(id, name, contact, details);

//            boolean isSaved = ItemModel.save(code, description, unitPrice, qtyOnHand);
            boolean isSaved = SupplierDAOImpl.add(supplierDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                //        setCellValueFactory();
                //        getAll();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void validateFields(String id, String name, String contact, String details) {
        // Check if all the required fields are not empty
        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateSupplierId(String id) {
        // Check if booking ID is valid using a regular expression
        String scheduleIdRegex = "^S\\d+$";
        if (!id.matches(scheduleIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid schedule ID starting with 'S' followed by one or more digits (e.g. S123)!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtDetails.clear();
    }

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            SupplierDTO supplierDTO = SupplierDAOImpl.search(txtId.getText());
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
}
