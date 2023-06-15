package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodBOImpl {
    public static ArrayList<String> loadIds() throws SQLException {
        ArrayList<String> allFoodIds = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT foodId FROM food");
        while (rst.next()) {
            allFoodIds.add(rst.getString(1));
        }
        return allFoodIds;
    }
}
