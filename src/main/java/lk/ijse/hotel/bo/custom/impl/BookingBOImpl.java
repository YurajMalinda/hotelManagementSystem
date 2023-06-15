package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingBOImpl {
    public static void releaseRoom(String roomId) throws SQLException {
        String release = "Booked";
        String sql = SQLUtil.execute("UPDATE room SET roomDetails = ? WHERE roomId = ?", roomId);

        PreparedStatement st = DBConnection.getInstance().getConnection().prepareStatement(sql);
        st.setString(1, release);
        st.setString(2, roomId);
        st.executeUpdate();
    }

    public static ArrayList<String> loadBookingIds() throws SQLException {
        ArrayList<String> allBookingIds = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT bookingId FROM booking");
        while (rst.next()) {
            allBookingIds.add(rst.getString(1));
        }
        return allBookingIds;
    }
}
