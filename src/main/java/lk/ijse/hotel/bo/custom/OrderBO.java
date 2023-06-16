package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.OrderDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;
import lk.ijse.hotel.entity.FoodOrderDetails;
import lk.ijse.hotel.entity.FoodOrders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO {
    public boolean saveOrder(OrderDTO dto);
    public ArrayList<OrderDTO> getAllFoodOrders() throws SQLException;
    public String generateNewOrderID() throws SQLException, ClassNotFoundException;
    public ArrayList<OrderDetailsDTO> getAllFoodOrderDetails() throws SQLException;
}
