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
import lk.ijse.hotel.dto.Room;
import lk.ijse.hotel.dto.tm.RoomTM;
import lk.ijse.hotel.model.RoomModel;

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
            List<Room> roomList = RoomModel.getAll();

            for (Room room : roomList) {
                obList.add(new RoomTM(
                        room.getId(),
                        room.getDetails(),
                        room.getRoomType(),
                        room.getPrice()
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
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) roomPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) roomPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        try {
            boolean isDeleted = RoomModel.delete(id);
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
            boolean isUpdated = RoomModel.update(id, details, roomType, price);
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

            Room room = new Room(id, details, roomType, price);
            boolean isSaved = RoomModel.add(room);
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
            Room room = RoomModel.search(txtId.getText());
            if (room != null) {
                txtId.setText(room.getId());
                txtDetails.setText(room.getDetails());
                txtRoomType.setText(room.getRoomType());
                txtPrice.setText(String.valueOf(room.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }
}
