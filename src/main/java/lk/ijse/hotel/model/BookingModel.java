package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Booking;
import lk.ijse.hotel.dto.OrderDetails;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM booking WHERE bookingId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(String bookingId, String guestId, String roomId, String checkOut) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE booking SET guestId = ?, roomId = ?, checkOut = ? WHERE bookingId = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, guestId);
            pstm.setString(2, roomId);
            pstm.setDate(3, Date.valueOf(checkOut));
            pstm.setString(4, bookingId);

            return pstm.executeUpdate() > 0;
    }

    public static boolean add(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking(guestId, bookingId, bookingDate, roomId, checkIn, checkOut) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                booking.getGuestId(),
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getRoomId(),
                booking.getCheckIn(),
                booking.getCheckOut());

    }

    public static Booking search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM booking WHERE bookingId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Booking(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

     public static List<Booking> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM booking";
        List<Booking> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();

        while (resultSet.next()) {
            data.add(new Booking(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }
    public static void releaseRoom(String roomId) throws SQLException {
        String release = "Booked";
        String sql4 = "UPDATE room SET roomDetails = ? WHERE roomId = ?";

        PreparedStatement st = DBConnection.getInstance().getConnection().prepareStatement(sql4);
        st.setString(1, release);
        st.setString(2, roomId);
        st.executeUpdate();
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT bookingId FROM booking");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Booking searchById(String code) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM booking WHERE bookingId = ?");
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Booking(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            );
        }
        return null;
    }

}
