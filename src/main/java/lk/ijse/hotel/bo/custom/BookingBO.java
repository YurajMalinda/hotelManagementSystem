package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.bo.SuperBO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.BookingDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingBO extends SuperBO {
    public boolean deleteBooking(String id) throws SQLException;
    public boolean updateBooking(BookingDTO dto) throws SQLException;
    public boolean addBooking(BookingDTO dto) throws SQLException;
    public BookingDTO searchBooking(String id) throws SQLException;
    public ArrayList<BookingDTO> getAllBookings() throws SQLException;
    public String generateNewBookingID() throws SQLException, ClassNotFoundException;
    public void releaseRoom(String roomId) throws SQLException;
}
