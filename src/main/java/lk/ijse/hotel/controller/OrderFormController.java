package lk.ijse.hotel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hotel.dto.BookingDTO;
import lk.ijse.hotel.dto.FoodDTO;
import lk.ijse.hotel.dto.GuestDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;
import lk.ijse.hotel.tm.OrderTM;
import lk.ijse.hotel.dao.OrderDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public AnchorPane orderPane;
    public TableView <OrderTM> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colBookingId;
    public TableColumn colGuestId;
    public TableColumn colGuestName;
    public TableColumn colOrderDate;
    public TableColumn colFoodId;
    public TableColumn colFoodName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colTotal;
    public TextField txtOrderId;
    public Label lblGuestId;
    public Label lblName;
    public Label lblRoomId;
    public Label lblOrderDate;
    public Label lblFoodName;
    public ComboBox <String>cmbBookingId;
    public ComboBox <String>cmbFoodId;
    public TextField txtQty;
    public Label lblPrice;
    public TableColumn colAction;
    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBookingIds();
        loadFoodIds();
        setOrderDate();
        setValueFactory();
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Stage stage = (Stage) orderPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/receptionist_form.fxml"));

            Stage stage = (Stage) orderPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }
    }
    void setValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestID"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("FoodID"));
        colFoodName.setCellValueFactory(new PropertyValueFactory<>("FoodName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("FoodPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

    }


    /*public void btnAddToCartOnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().isBlank() && !txtOrderId.getText().isBlank()) {
            String code = cmbFoodId.getValue();
            String description = lblFoodName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblPrice.getText());
            double total = qty * unitPrice;

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblOrder.getItems().size(); i++) {
                    if (colFoodId.getCellData(i).equals(code)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(total);

                        tblOrder.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            OrderTM tm = new OrderTM(txtOrderId.getText(), cmbBookingId.getValue(), lblGuestId.getText(), lblName.getText(), lblOrderDate.getText(), code, description, unitPrice, qty, total);

            obList.add(tm);
            tblOrder.setItems(obList);
            calculateNetTotal();
        }
    }*/

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().isBlank() && !txtOrderId.getText().isBlank()) {
            String code = cmbFoodId.getValue();
            String description = lblFoodName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblPrice.getText());
            double total = qty * unitPrice;
            Button removeBtn = new Button("Remove");
            removeBtn.setCursor(Cursor.HAND);
            setRemoveBtnOnAction(removeBtn);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblOrder.getItems().size(); i++) {
                    if (colFoodId.getCellData(i).equals(code)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(total);

                        tblOrder.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            OrderTM tm = new OrderTM(txtOrderId.getText(), cmbBookingId.getValue(), lblGuestId.getText(), lblName.getText(), lblOrderDate.getText(), code, description, unitPrice, qty, total, removeBtn);

            obList.add(tm);
            tblOrder.setItems(obList);
            calculateNetTotal();
        }
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index=tblOrder.getItems().size()-1;
                obList.remove(index);

                tblOrder.refresh();
                calculateNetTotal();
            }
        });
    }



    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            double total = (double) colTotal.getCellData(i);
            netTotal += total;
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String oId = txtOrderId.getText();
        String cusId = cmbBookingId.getValue();

        List<OrderDetailsDTO> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            OrderTM orderTM = obList.get(i);

            OrderDetailsDTO dto = new OrderDetailsDTO(
                    orderTM.getOrderID(),
                    orderTM.getFoodID(),
                    orderTM.getQty(),
                    orderTM.getTotal(),
                    orderTM.getOrderDate()
            );
            cartDTOList.add(dto);
        }
        String date = lblOrderDate.getText();
        boolean isPlaced = false;
        try {
            isPlaced = OrderDAOImpl.placeOrder(cartDTOList,oId,cusId);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();
                obList.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
    }
    private void loadBookingIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = BookingModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbBookingId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void loadFoodIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = FoodModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbFoodId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillMealFields(FoodDTO foodDTO) {
        lblFoodName.setText(foodDTO.getName());
        lblPrice.setText(String.valueOf(foodDTO.getPrice()));
    }

    public void bookIdOnAction(ActionEvent actionEvent) {
        String code = cmbBookingId.getValue();
        try {
            BookingDTO res = BookingModel.searchById(code);
            String cod = res.getGuestId();
            GuestDTO ges = GuestModel.search(cod);
            String gesCode = ges.getName();
            fillBookFields(res,gesCode);

            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillBookFields(BookingDTO res, String gesCode) {
        lblGuestId.setText(res.getGuestId());
        lblRoomId.setText(res.getRoomId());
        lblName.setText(gesCode);
    }

    public void foodIdOnAction(ActionEvent actionEvent) {
        String code = cmbFoodId.getValue();
        try {
            FoodDTO foodDTO = FoodModel.searchById(code);
            fillMealFields(foodDTO);

            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }
}
