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
import lk.ijse.hotel.dto.RoomDTO;
import lk.ijse.hotel.view.tdm.RoomTM;
import lk.ijse.hotel.dao.RoomDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
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
    @FXML
    private AnchorPane roomPane;

    public void initialize() {
        setCellValueFactory();
        getAll();
        setSelectToTxt();
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
            boolean isUpdated = RoomDAOImpl.update(id, details, roomType, price);
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

            validateFields(id, details, roomType);

            // Validate room ID
            validateRoomId(id);

            RoomDTO roomDTO = new RoomDTO(id, details, roomType, price);
            boolean isSaved = RoomDAOImpl.add(roomDTO);
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

    public void validateFields(String id, String details, String roomType) {
        // Check if all the required fields are not empty
        if (id.isEmpty() || details.isEmpty() || roomType.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }
    }

    public void validateRoomId(String id) {
        // Check if room ID is valid using a regular expression
        String employeeIdRegex = "^R\\d+$";
        if (!id.matches(employeeIdRegex)) {
            throw new IllegalArgumentException("Please enter a valid employee ID starting with 'E' followed by one or more digits (e.g. E123)!");
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtDetails.clear();
        txtRoomType.clear();
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
}
