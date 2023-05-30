package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.OrderDetailsDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl {
    public static boolean placeOrder(List<OrderDetailsDTO> cartDTOList, String oId, String cusId) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = PlaceOrderDAOImpl.save(oId, LocalDate.now(),cusId);
            if(isSaved) {
                boolean isUpdated = PlaceOrderDAOImpl.saveOrderDetails(cartDTOList);
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
