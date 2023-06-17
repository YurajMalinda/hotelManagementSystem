package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.RoomBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.RoomDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.RoomDTO;
import lk.ijse.hotel.entity.Room;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    
    @Override
    public boolean deleteRoom(String id) throws SQLException {
        return roomDAO.delete(id);    
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws SQLException {
        return roomDAO.update(new Room(dto.getId(), dto.getDetails(), dto.getRoomType(), dto.getPrice()));
    }

    @Override
    public boolean addRoom(RoomDTO dto) throws SQLException {
        return roomDAO.add(new Room(dto.getId(), dto.getDetails(), dto.getRoomType(), dto.getPrice()));
    }

    @Override
    public RoomDTO searchRoom(String id) throws SQLException {
        Room r = roomDAO.search(id);
        return new RoomDTO(r.getRoomId(), r.getRoomDetails(), r.getRoomType(), r.getRoomPrice());
    }

    @Override
    public ArrayList<RoomDTO> getAllRooms() throws SQLException {
        ArrayList<Room> all = roomDAO.getAll();
        ArrayList<RoomDTO> allRoomDetails = new ArrayList<>();
        for(Room r : all){
            allRoomDetails.add(new RoomDTO(r.getRoomId(), r.getRoomDetails(), r.getRoomType(), r.getRoomPrice()));
        }
        return allRoomDetails;
    }

    @Override
    public String generateNewRoomID() throws SQLException, ClassNotFoundException {
        return roomDAO.generateNewID();
    }

    @Override
    public void releaseRoom(String roomId, String release) throws SQLException {
        String sql = "UPDATE room SET roomDetails = ? WHERE roomId = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, release);
        pstm.setString(2, roomId);
        pstm.executeUpdate();
    }
}
