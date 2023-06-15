package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.BookingDAO;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.BookingDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import java.sql.*;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM booking WHERE bookingId = ?",id);
    }

    @Override
    public boolean update(BookingDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE booking SET guestId = ?, roomId = ?, checkOut = ? WHERE bookingId = ?", dto.getGuestId(), dto.getRoomId(), dto.getCheckOut(), dto.getBookingId());
    }

    @Override
    public boolean add(BookingDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO booking(guestId, bookingId, bookingDate, roomId, checkIn, checkOut)VALUES(?, ?, ?, ?, ?, ?)",dto.getGuestId(),dto.getBookingId(),dto.getBookingDate(),dto.getRoomId(),dto.getCheckIn(),dto.getCheckOut());
    }

    @Override
    public BookingDTO search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM booking WHERE bookingId = ?",id);
        if(rst.next()){
            return new BookingDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
        }
        return null;
    }

    @Override
    public ArrayList<BookingDTO> getAll() throws SQLException {
        ArrayList<BookingDTO> allBookings= new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM booking");
        while (rst.next()){
            allBookings.add(new BookingDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6)));
        }
        return allBookings;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT bookingId FROM booking ORDER BY bookingId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newBookingId = Integer.parseInt(id.replace("B00-", "")) + 1;
            return String.format("B00-%03d", newBookingId);
        } else {
            return "B00-001";
        }
    }
}
