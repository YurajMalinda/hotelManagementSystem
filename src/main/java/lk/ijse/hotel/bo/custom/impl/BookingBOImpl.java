package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.BookingBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.BookingDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.BookingDTO;
import lk.ijse.hotel.entity.Booking;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingBOImpl implements BookingBO {
    BookingDAO bookingDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.BOOKING);
    @Override
    public boolean deleteBooking(String id) throws SQLException {
        return bookingDAO.delete(id);
    }

    @Override
    public boolean updateBooking(BookingDTO dto) throws SQLException {
        return bookingDAO.update(new Booking(dto.getGuestId(), dto.getRoomId(), dto.getCheckOut(), dto.getBookingId()));
    }

    @Override
    public boolean addBooking(BookingDTO dto) throws SQLException {
        return bookingDAO.add(new Booking(dto.getGuestId(),dto.getBookingId(),dto.getBookingDate(),dto.getRoomId(),dto.getCheckIn(),dto.getCheckOut()));
    }

    @Override
    public BookingDTO searchBooking(String id) throws SQLException {
        Booking b=bookingDAO.search(id);
        return new BookingDTO(b.getGuestId(), b.getBookingId(), b.getBookingDate(), b.getRoomId(), b.getCheckIn(), b.getCheckOut());
    }

    @Override
    public ArrayList<BookingDTO> getAllBookings() throws SQLException {
        ArrayList<Booking> all = bookingDAO.getAll();
        ArrayList<BookingDTO> allBookings= new ArrayList<>();
        for (Booking c:all){
            allBookings.add(new BookingDTO(c.getGuestId(), c.getBookingId(), c.getBookingDate(), c.getRoomId(), c.getCheckIn(), c.getCheckOut()));
        }
        return allBookings;
    }

    @Override
    public String generateNewBookingID() throws SQLException, ClassNotFoundException {
        return bookingDAO.generateNewID();
    }

    @Override
    public void releaseRoom(String roomId) throws SQLException {
        String release = "Booked";
        String sql = SQLUtil.execute("UPDATE room SET roomDetails = ? WHERE roomId = ?", roomId);

        PreparedStatement st = DBConnection.getInstance().getConnection().prepareStatement(sql);
        st.setString(1, release);
        st.setString(2, roomId);
        st.executeUpdate();
    }
}
