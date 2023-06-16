package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.GuestBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.GuestDAO;
import lk.ijse.hotel.dto.GuestDTO;
import lk.ijse.hotel.entity.Guest;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuestBOImpl implements GuestBO {
    GuestDAO guestDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.GUEST);
    @Override
    public boolean deleteGuest(String id) throws SQLException {
        return guestDAO.delete(id);
    }

    @Override
    public boolean updateGuest(GuestDTO dto) throws SQLException {
        return guestDAO.update(new Guest(dto.getUserId(), dto.getId(), dto.getGender(), dto.getName(), dto.getCountry(), dto.getZipCode(), dto.getPassportId()));
    }

    @Override
    public boolean addGuest(GuestDTO dto) throws SQLException {
        return guestDAO.add(new Guest(dto.getUserId(), dto.getUserId(), dto.getName(), dto.getGender(), dto.getCountry(), dto.getZipCode(), dto.getPassportId()));
    }

    @Override
    public GuestDTO searchGuest(String id) throws SQLException {
        Guest g = guestDAO.search(id);
        return new GuestDTO(g.getUserId(), g.getGuestId(), g.getGuestName(), g.getGender(), g.getGuestCountry(), g.getGuestZipcode(), g.getGuestPassportId());
    }

    @Override
    public ArrayList<GuestDTO> getAllGuests() throws SQLException {
        ArrayList<Guest> all = guestDAO.getAll();
        ArrayList<GuestDTO> allGuestDetails = new ArrayList<>();
        for(Guest g : all){
            allGuestDetails.add(new GuestDTO(g.getUserId(), g.getGuestId(), g.getGuestName(), g.getGender(), g.getGuestCountry(), g.getGuestZipcode(), g.getGuestPassportId()));
        }
        return allGuestDetails;
    }

    @Override
    public String generateNewGuestID() throws SQLException, ClassNotFoundException {
        return guestDAO.generateNewID();
    }
}
