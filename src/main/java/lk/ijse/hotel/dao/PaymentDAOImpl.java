package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl {
    public static boolean add(PaymentDTO paymentDTO) throws SQLException {
        String sql = "INSERT INTO Payment(paymentId , guestId  , guestName  , bookingId  , roomId  ,checkInDate  ,checkOutDate  ,ordersAmount  , totalPrice  )" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, paymentDTO.getPaymentId());
        pstm.setString(2, paymentDTO.getGuestId());
        pstm.setString(3, paymentDTO.getGuestName());
        pstm.setString(4, paymentDTO.getResId());
        pstm.setString(5, paymentDTO.getRoomId());
        pstm.setString(6, paymentDTO.getCheckIn());
        pstm.setString(7, paymentDTO.getCheckOut());
        pstm.setDouble(8, paymentDTO.getOrderAm());
        pstm.setDouble(9, paymentDTO.getTotal());

        int affectedRows = pstm.executeUpdate();

        return affectedRows > 0;
    }

    public static boolean update(PaymentDTO paymentDTO) throws SQLException {
            String sql = "UPDATE Payment SET guestId = ?, guestName = ?, bookingId = ?, roomId = ?, checkInDate = ?, checkOutDate = ?, ordersAmount = ?, totalPrice = ? WHERE paymentId = ?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1, paymentDTO.getGuestId());
            pstm.setString(2, paymentDTO.getGuestName());
            pstm.setString(3, paymentDTO.getResId());
            pstm.setString(4, paymentDTO.getRoomId());
            pstm.setString(5, paymentDTO.getCheckIn());
            pstm.setString(6, paymentDTO.getCheckOut());
            pstm.setDouble(7, paymentDTO.getOrderAm());
            pstm.setDouble(8, paymentDTO.getTotal());
            pstm.setString(9, paymentDTO.getPaymentId());


            int affectedRows = pstm.executeUpdate();

            return affectedRows > 0;
    }

    public static boolean delete(String paymentId) throws SQLException {
        String sql = "DELETE FROM Payment WHERE paymentId = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, paymentId);

        return pstm.executeUpdate() > 0;
    }
    public static List<PaymentDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Payment";
        List<PaymentDTO> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        while (resultSet.next()) {
            data.add(new PaymentDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8),
                    resultSet.getDouble(9)
            ));
        }
        return data;
    }
}
