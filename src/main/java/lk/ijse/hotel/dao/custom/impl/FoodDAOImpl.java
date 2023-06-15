package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.FoodDAO;
import lk.ijse.hotel.dto.FoodDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FoodDAOImpl implements FoodDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM food WHERE foodId = ?", id);
    }

    @Override
    public boolean update(FoodDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE food SET foodName = ?, foodDetails = ?, foodPrice = ? WHERE foodId = ?", dto.getName(), dto.getDetails(), dto.getPrice(), dto.getId());
    }

    @Override
    public boolean add(FoodDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO food(foodId, foodName, foodDetails, foodPrice) VALUES(?, ?, ?, ?)", dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice());
    }

    @Override
    public FoodDTO search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM food WHERE foodId = ?", id);
        if(rst.next()) {
            return new FoodDTO(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getDouble(4));
        }
        return null;
    }

    @Override
    public ArrayList<FoodDTO> getAll() throws SQLException {
        ArrayList<FoodDTO> allFoodDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM food");
        while (rst.next()) {
            allFoodDetails.add(new FoodDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allFoodDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT foodId FROM food ORDER BY foodId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newFoodId = Integer.parseInt(id.replace("F00-", "")) + 1;
            return String.format("F00-%03d", newFoodId);
        } else {
            return "F00-001";
        }
    }
}
