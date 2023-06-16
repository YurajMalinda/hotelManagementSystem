package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.PaymentBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.PaymentDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.PaymentDTO;
import lk.ijse.hotel.entity.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public boolean deletePayment(String id) throws SQLException {
        return paymentDAO.delete(id);
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) throws SQLException {
        return paymentDAO.update(new Payment(dto.getPaymentId(), dto.getGuestId(), dto.getGuestName(), dto.getResId(), dto.getRoomId(), dto.getCheckIn(), dto.getCheckOut(), dto.getOrderAm(), dto.getTotal()));
    }

    @Override
    public boolean addPayment(PaymentDTO dto) throws SQLException {
        return paymentDAO.add(new Payment(dto.getPaymentId(), dto.getGuestId(), dto.getGuestName(), dto.getResId(), dto.getRoomId(), dto.getCheckIn(), dto.getCheckOut(), dto.getOrderAm(), dto.getTotal()));
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ArrayList<Payment> all = paymentDAO.getAll();
        ArrayList<PaymentDTO> allPaymentDetails = new ArrayList<>();
        for(Payment p : all) {
            allPaymentDetails.add(new PaymentDTO(p.getPaymentId(), p.getGuestId(), p.getGuestName(), p.getBookingId(), p.getRoomId(), p.getCheckInDate(), p.getCheckOutDate(), p.getOrdersAmount(), p.getTotalPrice()));
        }
        return allPaymentDetails;        
    }

    @Override
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public Double getOrderAmount(String id) throws SQLException {
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
