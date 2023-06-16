package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.OrderDetailsDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.FoodOrderDetails;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean update(FoodOrderDetails entity) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean add(FoodOrderDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO foodOrderDetail(orderId , foodId   , qty   ,amount  ,date ) VALUES(?, ?, ?, ?, ?)", entity.getOrderId(), entity.getFoodId(), entity.getQty(), entity.getAmount(), entity.getDate());
    }

    @Override
    public FoodOrderDetails search(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<FoodOrderDetails> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM foodorderDetails");
        ArrayList<FoodOrderDetails> allOrderDetails = new ArrayList<>();
        while (rst.next()) {
            allOrderDetails.add(new FoodOrderDetails(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getString(5)));
        }
        return allOrderDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}
