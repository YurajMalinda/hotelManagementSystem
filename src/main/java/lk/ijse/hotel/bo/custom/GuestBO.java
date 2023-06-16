package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.GuestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuestBO {
    public boolean deleteGuest(String id) throws SQLException;
    public boolean updateGuest(GuestDTO dto) throws SQLException;
    public boolean addGuest(GuestDTO dto) throws SQLException;
    public GuestDTO searchGuest(String id) throws SQLException;
    public ArrayList<GuestDTO> getAllGuests() throws SQLException;
    public String generateNewGuestID() throws SQLException, ClassNotFoundException;
}
