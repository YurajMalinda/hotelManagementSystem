package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.OrderDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.FoodOrders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean update(FoodOrders entity) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean add(FoodOrders entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO foodOrders(orderId, date , bookingId ) VALUES(?, ?, ?)", entity.getOrderId(), entity.getDate(), entity.getBookingId());
    }

    @Override
    public FoodOrders search(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<FoodOrders> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM foodorders");
        ArrayList<FoodOrders> allOrders = new ArrayList<>();
        while (rst.next()) {
            allOrders.add(new FoodOrders(rst.getString(1), rst.getString(2), rst.getString(3)));
        }
        return allOrders;    }

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
