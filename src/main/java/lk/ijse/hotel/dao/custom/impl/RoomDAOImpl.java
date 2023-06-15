package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.RoomDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.dto.RoomDTO;

import java.sql.*;
import java.util.ArrayList;


public class RoomDAOImpl implements RoomDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM room WHERE roomId = ?", id);
    }

    @Override
    public boolean update(RoomDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE Room SET roomDetails = ?, roomType = ?, roomPrice = ? WHERE roomId = ?", dto.getDetails(), dto.getRoomType(), dto.getPrice(), dto.getId());

    }

    @Override
    public boolean add(RoomDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Room(roomId, roomDetails, roomType, roomPrice) VALUES(?, ?, ?, ?)", dto.getId(), dto.getDetails(), dto.getRoomType(), dto.getPrice());
    }

    @Override
    public RoomDTO search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM room WHERE roomId = ?", id);
        if(rst.next()) {
            return new RoomDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
        }
        return null;
    }

    @Override
    public ArrayList<RoomDTO> getAll() throws SQLException {
        ArrayList<RoomDTO> allRoomDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM room");
        while (rst.next()) {
            allRoomDetails.add(new RoomDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allRoomDetails;
    }

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
