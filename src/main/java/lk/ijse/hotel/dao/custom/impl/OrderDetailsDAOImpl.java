package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.OrderDetailsDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.dto.OrderDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean update(OrderDetailsDTO dto) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean add(OrderDetailsDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO foodOrderDetail(orderId , foodId   , qty   ,amount  ,date ) VALUES(?, ?, ?, ?, ?)", dto.getOrderID(), dto.getMealID(), dto.getQty(), dto.getTotal(), dto.getDate());
    }

    @Override
    public OrderDetailsDTO search(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM foodorderDetails");
        ArrayList<OrderDetailsDTO> allOrderDetails = new ArrayList<>();
        while (rst.next()) {
            allOrderDetails.add(new OrderDetailsDTO(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getString(5)));
        }
        return allOrderDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}
