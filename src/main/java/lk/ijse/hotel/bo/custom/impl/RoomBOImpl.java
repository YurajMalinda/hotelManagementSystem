package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomBOImpl {
    public static ArrayList<String> loadRoomIds() throws SQLException {
        ArrayList<String> allRoomIds = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT roomId FROM room WHERE roomDetails = 'Available'");
        while (rst.next()) {
            allRoomIds.add(rst.getString(1));
        }
        return allRoomIds;
    }

    public static void releaseRoom(String roomId, String release) throws SQLException {
        SQLUtil.execute("UPDATE room SET roomDetails = ? WHERE roomId = ?", release, roomId);
    }
}
