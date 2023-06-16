package lk.ijse.hotel.dao.custom;

import lk.ijse.hotel.dao.CrudDAO;
import lk.ijse.hotel.dto.OrderDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;
import lk.ijse.hotel.entity.FoodOrders;

public interface OrderDAO extends CrudDAO<FoodOrders, String> {
}
