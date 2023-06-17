package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.bo.SuperBO;
import lk.ijse.hotel.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    public boolean deletePayment(String id) throws SQLException;
    public boolean updatePayment(PaymentDTO dto) throws SQLException;
    public boolean addPayment(PaymentDTO dto) throws SQLException;
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException;
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException;
    public Double getOrderAmount(String id) throws SQLException;
}
