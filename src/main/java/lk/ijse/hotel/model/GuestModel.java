package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Guest;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestModel {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM guest WHERE guestId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(String userId, String id, String name, String gender, String country, String zipcode, String passportId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE guest SET userId = ?, guestName = ?, gender = ?, guestCountry = ?, guestZipcode = ?, guestPassportId = ? WHERE guestId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, userId);
        pstm.setString(2, name);
        pstm.setString(3, gender);
        pstm.setString(4, country);
        pstm.setString(5, zipcode);
        pstm.setString(6, passportId);
        pstm.setString(7, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(Guest guest) throws SQLException {
        String sql = "INSERT INTO guest(userId, guestId, guestName, gender, guestCountry, guestZipcode, guestPassportId) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                guest.getUserId(),
                guest.getId(),
                guest.getName(),
                guest.getGender(),
                guest.getCountry(),
                guest.getZipCode(),
                guest.getPassportId());
    }

    public static Guest search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM guest WHERE guestId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Guest(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7)
            );
        }
        return null;
    }

    public static List<Guest> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM guest";
        List<Guest> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Guest(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT guestId FROM guest");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}
