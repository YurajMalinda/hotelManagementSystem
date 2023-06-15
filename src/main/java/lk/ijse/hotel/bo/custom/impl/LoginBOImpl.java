package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBOImpl {
    public static ArrayList<String> loadTitles() throws SQLException {
        ArrayList<String> allTitles = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT title FROM user");
        while (rst.next()) {
            allTitles.add(rst.getString(1));
        }
        return allTitles;
    }
}
