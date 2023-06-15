package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl {
    public static Double getOrderAmount(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM foodOrders WHERE bookingId = ?", id);
        ArrayList<String> orderIds = new ArrayList<>();
        while (rst.next()) {
            String orderId = rst.getString("orderId");
            orderIds.add(orderId);
        }

        double totalAmount = 0.0;
        String sql2 = "SELECT amount FROM foodOrderDetail WHERE orderId = ?";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql2);
        for (String orderId : orderIds) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            double orderAmount = 0.0;
            while (rs.next()) {
                orderAmount += rs.getDouble("amount");
            }

            totalAmount += orderAmount;
        }
        return totalAmount;
    }
}
