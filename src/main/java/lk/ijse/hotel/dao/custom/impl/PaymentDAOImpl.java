package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.PaymentDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Payment WHERE paymentId = ?", id);
    }

    @Override
    public boolean update(PaymentDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE Payment SET guestId = ?, guestName = ?, bookingId = ?, roomId = ?, checkInDate = ?, checkOutDate = ?, ordersAmount = ?, totalPrice = ? WHERE paymentId = ?", dto.getGuestId(), dto.getGuestName(), dto.getResId(), dto.getRoomId(), dto.getCheckIn(), dto.getCheckOut(), dto.getOrderAm(), dto.getTotal(), dto.getPaymentId());
    }

    @Override
    public boolean add(PaymentDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Payment(paymentId , guestId  , guestName  , bookingId  , roomId  ,checkInDate  ,checkOutDate  ,ordersAmount  , totalPrice  ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", dto.getPaymentId(), dto.getGuestId(), dto.getGuestName(), dto.getResId(), dto.getRoomId(), dto.getCheckIn(), dto.getCheckOut(), dto.getOrderAm(), dto.getTotal());
    }

    @Override
    public PaymentDTO search(String id) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException {
        ArrayList<PaymentDTO> allPaymentDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Payment");
        while (rst.next()) {
            allPaymentDetails.add(new PaymentDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getDouble(8), rst.getDouble(9)));
        }
        return allPaymentDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newPaymentId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newPaymentId);
        } else {
            return "P00-001";
        }
    }
}
