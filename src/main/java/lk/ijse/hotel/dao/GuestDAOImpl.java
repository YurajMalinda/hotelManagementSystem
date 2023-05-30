package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.GuestDTO;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDAOImpl {
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

    public static boolean add(GuestDTO guestDTO) throws SQLException {
        String sql = "INSERT INTO guest(userId, guestId, guestName, gender, guestCountry, guestZipcode, guestPassportId) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                guestDTO.getUserId(),
                guestDTO.getId(),
                guestDTO.getName(),
                guestDTO.getGender(),
                guestDTO.getCountry(),
                guestDTO.getZipCode(),
                guestDTO.getPassportId());
    }

    public static GuestDTO search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM guest WHERE guestId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new GuestDTO(
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

    public static List<GuestDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM guest";
        List<GuestDTO> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new GuestDTO(
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
