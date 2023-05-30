package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.OrderDetailsDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDAOImpl {

    public static boolean save(String oId, LocalDate now, String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO foodOrders(orderId, date , bookingId ) VALUES(?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setDate(2, Date.valueOf(now));
        pstm.setString(3, cusId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean saveOrderDetails(List<OrderDetailsDTO> cartDTOList) throws SQLException {
        for(OrderDetailsDTO orderDetailsDTO : cartDTOList) {
            if(!updateQty(orderDetailsDTO)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO foodOrderDetail(orderId , foodId   , qty   ,amount  ,date ) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, orderDetailsDTO.getOrderID());
        pstm.setString(2, orderDetailsDTO.getMealID());
        pstm.setInt(3, orderDetailsDTO.getQty());
        pstm.setDouble(4, orderDetailsDTO.getTotal());
        pstm.setDate(5, Date.valueOf(orderDetailsDTO.getDate()));

        return pstm.executeUpdate() > 0;
    }

    public static Double getOrderAm(String code) throws SQLException {

            String sql = "SELECT orderId FROM foodOrders WHERE bookingId = ?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1, code);
            ResultSet rs = pstm.executeQuery();


            ArrayList<String> orderIds = new ArrayList<>();
            while (rs.next()) {
                String orderId = rs.getString("orderId");
                orderIds.add(orderId);
            }

            double totalAmount = 0.0;
            String sql2 = "SELECT amount FROM foodOrderDetail WHERE orderId = ?";
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql2);
            for (String orderId : orderIds) {
                pst.setString(1, orderId);
                ResultSet rst = pst.executeQuery();
                double orderAmount = 0.0;
                while (rst.next()) {
                    orderAmount += rst.getDouble("amount");
                }

                totalAmount += orderAmount;
            }
            return totalAmount;
    }
}
