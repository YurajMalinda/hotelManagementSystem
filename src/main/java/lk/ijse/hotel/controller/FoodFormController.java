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
import lk.ijse.hotel.bo.custom.FoodBO;
import lk.ijse.hotel.dto.FoodDTO;
import lk.ijse.hotel.dto.tdm.FoodTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtPrice;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDetails;
    public TableColumn colPrice;
    public TableView <FoodTM> tblFood;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    public TextField txtDetails;
    @FXML
    private AnchorPane foodPane;

    FoodBO foodBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.FOOD_BO);

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
        txtId.setEditable(false);
        txtId.setDisable(true);
        txtPrice.setDisable(true);
        txtDetails.setDisable(true);
        txtName.setDisable(true);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        txtPrice.setOnAction(event -> btnAdd.fire());
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
            List<FoodDTO> foodDTOList = foodBO.getAllFoods();

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
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) foodPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) foodPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = foodBO.deleteFood(id);
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

        if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = foodBO.updateFood(new FoodDTO(id, name, details, price));
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

            if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = foodBO.addFood(new FoodDTO(id, name, details, price));
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

    public void codeSearchOnAction(ActionEvent actionEvent) {
        try {
            FoodDTO foodDTO = (FoodDTO) foodBO.searchFood(txtId.getText());
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

    private String generateNewFoodId() {
        try {
            return foodBO.generateNewFoodID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblFood.getItems().isEmpty()) {
            return "F00-001";
        } else {
            String id = getLastFoodId();
            int newFoodId = Integer.parseInt(id.replace("F", "")) + 1;
            return String.format("F00-%03d", newFoodId);
        }
    }

    private String getLastFoodId() {
        List<FoodTM> tempFoodList = new ArrayList<>(tblFood.getItems());
        Collections.sort(tempFoodList);
        return tempFoodList.get(tempFoodList.size() - 1).getId();
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtName.clear();
        txtId.setEditable(false);
        txtId.setText(generateNewFoodId());
        txtName.requestFocus();
        txtId.setDisable(false);
        txtPrice.setDisable(false);
        txtDetails.setDisable(false);
        txtName.setDisable(false);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        tblFood.getSelectionModel().clearSelection();
    }
}
