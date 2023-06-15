package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.GuestDAO;
import lk.ijse.hotel.dto.GuestDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuestDAOImpl implements GuestDAO{
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM guest WHERE guestId = ?", id);
    }

    @Override
    public boolean update(GuestDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE guest SET userId = ?, guestName = ?, gender = ?, guestCountry = ?, guestZipcode = ?, guestPassportId = ? WHERE guestId = ?", dto.getUserId(), dto.getName(), dto.getGender(), dto.getCountry(), dto.getZipCode(), dto.getPassportId(), dto.getId());
    }

    @Override
    public boolean add(GuestDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO guest(userId, guestId, guestName, gender, guestCountry, guestZipcode, guestPassportId) VALUES(?, ?, ?, ?, ?, ?, ?)", dto.getUserId(), dto.getId(), dto.getName(), dto.getGender(), dto.getCountry(), dto.getZipCode(), dto.getPassportId());
    }

    @Override
    public GuestDTO search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM guest WHERE guestId = ?", id);
        if (rst.next()) {
            return new GuestDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public ArrayList<GuestDTO> getAll() throws SQLException {
        ArrayList<GuestDTO> allGuestDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM guest");
        while (rst.next()) {
            allGuestDetails.add(new GuestDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return allGuestDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT guestId FROM guest ORDER BY guestId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newGuestId = Integer.parseInt(id.replace("G00-", "")) + 1;
            return String.format("G00-%03d", newGuestId);
        } else {
            return "G00-001";
        }
    }
}
