package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderModel {
    public static boolean placeOrder(List<OrderDetails> cartDTOList, String oId, String cusId) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = PlaceOrderModel.save(oId, LocalDate.now(),cusId);
            if(isSaved) {
                boolean isUpdated = PlaceOrderModel.saveOrderDetails(cartDTOList);
                if(isUpdated) {
                    con.commit();
                    return true;
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }
}
