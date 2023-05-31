package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.RoomDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM room WHERE roomId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(RoomDTO roomDTO) throws SQLException {
        String sql = "INSERT INTO Room(roomId, roomDetails, roomType, roomPrice) " +
                "VALUES(?, ?, ?, ?)";
        return SQLUtil.execute(
                sql,
                roomDTO.getId(),
                roomDTO.getDetails(),
                roomDTO.getRoomType(),
                roomDTO.getPrice());
    }

    public static boolean update(String id, String details, String roomType, Double price) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Room SET roomDetails = ?, roomType = ?, roomPrice = ? WHERE roomId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, details);
        pstm.setString(2, roomType);
        pstm.setDouble(3, price);
        pstm.setString(4, id);

        return pstm.executeUpdate() > 0;
    }

    public static RoomDTO search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM room WHERE roomId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new RoomDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static List<RoomDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM room";
        List<RoomDTO> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new RoomDTO(
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
        ResultSet resultSet = con.createStatement().executeQuery("SELECT roomId FROM room WHERE roomDetails = 'Available'");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static void releaseRoom(String roomId, String release) throws SQLException {
        String sql = "UPDATE room SET roomDetails = ? WHERE roomId = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, release);
        pstm.setString(2, roomId);
        pstm.executeUpdate();
    }
}
