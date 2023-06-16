package lk.ijse.hotel.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.hotel.bo.BOFactory;
import lk.ijse.hotel.bo.custom.BookingBO;
import lk.ijse.hotel.bo.custom.FoodBO;
import lk.ijse.hotel.bo.custom.GuestBO;
import lk.ijse.hotel.bo.custom.OrderBO;
import lk.ijse.hotel.dto.*;
import lk.ijse.hotel.view.tdm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    public JFXButton btnAdd;
    public JFXButton btnPlaceOrder;
    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    BookingBO bookingBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKING_BO);
    GuestBO guestBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.GUEST_BO);
    FoodBO foodBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.FOOD_BO);
    OrderBO orderBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDER_BO);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBookingIds();
        loadFoodIds();
        setOrderDate();
        setValueFactory();
        initUI();
    }

    private void initUI() {
        txtOrderId.setText(generateNewOrderId());
        txtOrderId.setEditable(false);
        txtQty.setOnAction(event -> btnAdd.fire());
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(BackButtonController.backButton == 1){
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/dashboard_form.fxml"));

            Stage stage = (Stage) orderPane.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/hotel/view/receptionist_form.fxml"));

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
        boolean b = saveOrder(txtOrderId.getText(), lblOrderDate.getText(), cmbBookingId.getValue(), tblOrder.getItems().stream().map(tm -> new OrderDetailsDTO(tm.getOrderID(), tm.getFoodID(), tm.getQty(), tm.getTotal(), tm.getOrderDate())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        txtOrderId.setText(generateNewOrderId());
        cmbBookingId.getSelectionModel().clearSelection();
        cmbFoodId.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        txtQty.clear();
        calculateNetTotal();
    }

    private boolean saveOrder(String orderId, String date, String bookingId, List<OrderDetailsDTO> orderDetails) {
        return orderBO.saveOrder(new OrderDTO(orderId, date, bookingId, orderDetails));
    }

    private void loadBookingIds() {
        try {
            ArrayList<BookingDTO> allBookings = bookingBO.getAllBookings();
            for (BookingDTO c : allBookings) {
                cmbBookingId.getItems().add(c.getBookingId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load booking ids").show();
        }
    }

    private void loadFoodIds() {
        try {
            ArrayList<FoodDTO> allFoods = foodBO.getAllFoods();
            for (FoodDTO c : allFoods) {
                cmbFoodId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load food ids").show();
        }
    }

    private void fillMealFields(FoodDTO foodDTO) {
        lblFoodName.setText(foodDTO.getName());
        lblPrice.setText(String.valueOf(foodDTO.getPrice()));
    }

    public void bookIdOnAction(ActionEvent actionEvent) {
        String id = cmbBookingId.getValue();
        try {
            BookingDTO res = (BookingDTO) bookingBO.searchBooking(id);
            String code = res.getGuestId();
            GuestDTO ges = (GuestDTO) guestBO.searchGuest(code);
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
        String id = cmbFoodId.getValue();
        try {
            FoodDTO foodDTO = (FoodDTO) foodBO.searchFood(id);
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

    private String generateNewOrderId() {
        try {
            return orderBO.generateNewOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "O00-001";
    }
}
