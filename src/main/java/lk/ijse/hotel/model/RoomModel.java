package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Room;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomModel {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM room WHERE roomId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(Room room) throws SQLException {
        String sql = "INSERT INTO Room(roomId, roomDetails, roomType, roomPrice) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                room.getId(),
                room.getDetails(),
                room.getRoomType(),
                room.getPrice());
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

    public static Room search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM room WHERE roomId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static List<Room> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM room";
        List<Room> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Room(
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
