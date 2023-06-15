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
import lk.ijse.hotel.dto.RoomDTO;
import lk.ijse.hotel.view.tdm.BookingTM;
import lk.ijse.hotel.view.tdm.RoomTM;
import lk.ijse.hotel.dao.custom.impl.RoomDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomFormController {

    public TableColumn colRoomType;
    public TableColumn colDetails;
    public TableColumn colId;
    public TableColumn colPrice;
    public TextField txtId;
    public TextField txtDetails;
    public TextField txtRoomType;
    public TextField txtPrice;
    public TableView<RoomTM> tblRoom;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnAddNew;
    @FXML
    private AnchorPane roomPane;

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
        txtRoomType.clear();
        txtId.setEditable(false);
        txtId.setDisable(true);
        txtPrice.setDisable(true);
        txtDetails.setDisable(true);
        txtRoomType.setDisable(true);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        txtPrice.setOnAction(event -> btnAdd.fire());
    }

    private void setSelectToTxt() {
        tblRoom.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtDetails.setText(newSelection.getDetails());
                txtRoomType.setText(newSelection.getRoomType());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void getAll() {
        try {
            ObservableList<RoomTM> obList = FXCollections.observableArrayList();
            List<RoomDTO> roomDTOList = RoomDAOImpl.getAll();

            for (RoomDTO roomDTO : roomDTOList) {
                obList.add(new RoomTM(
                        roomDTO.getId(),
                        roomDTO.getDetails(),
                        roomDTO.getRoomType(),
                        roomDTO.getPrice()
                ));
            }
            tblRoom.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) roomPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

            Stage stage = (Stage) roomPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        try {
            boolean isDeleted = RoomDAOImpl.delete(id);
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
        String details = txtDetails.getText();
        String roomType =txtRoomType.getText();
        Double price = Double.parseDouble(txtPrice.getText());

        try {
            boolean isUpdated = RoomDAOImpl.update(new RoomDTO(id, details, roomType, price));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Room updated!").show();
                getAll();
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Room updated!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String details = txtDetails.getText();
            String roomType =txtRoomType.getText();
            Double price = Double.parseDouble(txtPrice.getText());

            if (id.isEmpty() || details.isEmpty() || roomType.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = RoomDAOImpl.add(new RoomDTO(id, details, roomType, price));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Room saved!").show();
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
            RoomDTO roomDTO = RoomDAOImpl.search(txtId.getText());
            if (roomDTO != null) {
                txtId.setText(roomDTO.getId());
                txtDetails.setText(roomDTO.getDetails());
                txtRoomType.setText(roomDTO.getRoomType());
                txtPrice.setText(String.valueOf(roomDTO.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    private String generateNewRoomId() {
        try {
            return crudDAO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblRoom.getItems().isEmpty()) {
            return "R00-001";
        } else {
            String id = getLastRoomId();
            int newRoomId = Integer.parseInt(id.replace("R", "")) + 1;
            return String.format("R00-%03d", newRoomId);
        }
    }

    private String getLastRoomId() {
        List<RoomTM> tempRoomList = new ArrayList<>(tblRoom.getItems());
        Collections.sort(tempRoomList);
        return tempRoomList.get(tempRoomList.size() - 1).getId();
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtRoomType.clear();
        txtId.setDisable(false);
        txtId.setText(generateNewRoomId());
        txtDetails.requestFocus();
        txtPrice.setDisable(false);
        txtDetails.setDisable(false);
        txtId.setEditable(false);
        txtRoomType.setDisable(false);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        tblRoom.getSelectionModel().clearSelection();
    }
}

