package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Food;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodModel {

    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM food WHERE foodId = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            return pstm.executeUpdate() > 0;
    }

    public static boolean add(Food food) throws SQLException {
        String sql = "INSERT INTO food(foodId, foodName, foodDetails, foodPrice) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                food.getId(),
                food.getName(),
                food.getDetails(),
                food.getPrice());
    }

    public static boolean update(String id, String name, String details, Double price) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE food SET foodName = ?, foodDetails = ?, foodPrice = ? WHERE foodId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, details);
        pstm.setDouble(3, price);
        pstm.setString(4, id);

        return pstm.executeUpdate() > 0;
    }

    public static Food search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM food WHERE foodId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static List<Food> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM food";
        List<Food> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return data;
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT foodId FROM food");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Food searchById(String code) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM food WHERE foodId = ?");
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Food(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)


            );
        }
        return null;
    }
}
