package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.RoomDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Room;

import java.sql.*;
import java.util.ArrayList;


public class RoomDAOImpl implements RoomDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM room WHERE roomId = ?", id);
    }

    @Override
    public boolean update(Room entity) throws SQLException {
        return SQLUtil.execute("UPDATE Room SET roomDetails = ?, roomType = ?, roomPrice = ? WHERE roomId = ?", entity.getRoomDetails(), entity.getRoomType(), entity.getRoomPrice(), entity.getRoomId());
    }

    @Override
    public boolean add(Room entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO room(roomId, roomDetails, roomType, roomPrice) VALUES(?, ?, ?, ?)", entity.getRoomId(), entity.getRoomDetails(), entity.getRoomType(), entity.getRoomPrice());
    }

    @Override
    public Room search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM room WHERE roomId = ?", id);
        if(rst.next()) {
            return new Room(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
        }
        return null;    }

    @Override
    public ArrayList<Room> getAll() throws SQLException {
        ArrayList<Room> allRoomDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM room");
        while (rst.next()) {
            allRoomDetails.add(new Room(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allRoomDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT roomId FROM room ORDER BY roomId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("roomId");
            int newRoomId = Integer.parseInt(id.replace("R00-", "")) + 1;
            return String.format("R00-%03d", newRoomId);
        } else {
            return "R00-001";
        }    
    }
}
