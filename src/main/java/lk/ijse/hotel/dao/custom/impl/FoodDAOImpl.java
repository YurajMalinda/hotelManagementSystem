package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.FoodDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FoodDAOImpl implements FoodDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM food WHERE foodId = ?", id);
    }

    @Override
    public boolean update(Food entity) throws SQLException {
        return SQLUtil.execute("UPDATE food SET foodName = ?, foodDetails = ?, foodPrice = ? WHERE foodId = ?", entity.getFoodName(), entity.getFoodDetails(), entity.getFoodPrice(), entity.getFoodId());
    }

    @Override
    public boolean add(Food entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO food(foodId, foodName, foodDetails, foodPrice) VALUES(?, ?, ?, ?)", entity.getFoodId(), entity.getFoodName(), entity.getFoodDetails(), entity.getFoodPrice());
    }

    @Override
    public Food search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM food WHERE foodId = ?", id);
        if(rst.next()) {
            return new Food(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getDouble(4));
        }
        return null;
    }

    @Override
    public ArrayList<Food> getAll() throws SQLException {
        ArrayList<Food> allFoodDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM food");
        while (rst.next()) {
            allFoodDetails.add(new Food(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allFoodDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT foodId FROM food ORDER BY foodId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("foodId");
            int newFoodId = Integer.parseInt(id.replace("F00-", "")) + 1;
            return String.format("F00-%03d", newFoodId);
        } else {
            return "F00-001";
        }
    }
}
