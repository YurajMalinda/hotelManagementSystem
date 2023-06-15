package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourBOImpl {
    public static ArrayList<String> loadTourIds() throws SQLException {
        ArrayList<String> allTourIds = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT tourId FROM tour");
        while (rst.next()) {
            allTourIds.add(rst.getString(1));
        }
        return allTourIds;
    }
}
