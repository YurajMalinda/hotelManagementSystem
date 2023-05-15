package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Tour;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourModel {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM tour WHERE tourId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(Tour tour) throws SQLException {
        String sql = "INSERT INTO tour(tourId, tourName, tourDetails, Price) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                tour.getId(),
                tour.getName(),
                tour.getDetails(),
                tour.getPrice());
    }

    public static boolean update(String id, String name, String details, Double price) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE tour SET tourName = ?, tourDetails = ?, price = ? WHERE tourId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, details);
        pstm.setDouble(3, price);
        pstm.setString(4, id);

        return pstm.executeUpdate() > 0;
    }

    public static Tour search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tour WHERE tourId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Tour(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static List<Tour> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tour";
        List<Tour> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Tour(
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
        ResultSet resultSet = con.createStatement().executeQuery("SELECT tourId FROM tour");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}
