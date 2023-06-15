package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.OrderDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.OrderDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean add(OrderDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO foodOrders(orderId, date , bookingId ) VALUES(?, ?, ?)", dto.getOrderID(), dto.getDate(), dto.getBookingID());
    }

    @Override
    public OrderDTO search(String s) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM foodorders");
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        while (rst.next()) {
            allOrders.add(new OrderDTO(rst.getString(1), rst.getDate(2).toLocalDate(), rst.getString(3)));
        }
        return allOrders;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM foodOrders ORDER BY orderId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("orderId");
            int newOrderId = Integer.parseInt(id.replace("O00-", "")) + 1;
            return String.format("O00-%03d", newOrderId);
        } else {
            return "O00-001";
        }
    }
}
