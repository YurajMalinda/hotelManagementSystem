package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuestBOImpl {
    public static ArrayList<String> loadGuestIds() throws SQLException {
        ArrayList<String> allGuestIds = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT guestId FROM guest");
        while (rst.next()) {
            allGuestIds.add(rst.getString(1));
        }
        return allGuestIds;
    }
}
