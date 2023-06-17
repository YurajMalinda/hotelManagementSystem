package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.bo.SuperBO;
import lk.ijse.hotel.dto.RoomDTO;
import lk.ijse.hotel.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    public boolean deleteRoom(String id) throws SQLException;
    public boolean updateRoom(RoomDTO dto) throws SQLException;
    public boolean addRoom(RoomDTO dto) throws SQLException;
    public RoomDTO searchRoom(String id) throws SQLException;
    public ArrayList<RoomDTO> getAllRooms() throws SQLException;
    public String generateNewRoomID() throws SQLException, ClassNotFoundException;
    public void releaseRoom(String roomId, String release) throws SQLException;
}
