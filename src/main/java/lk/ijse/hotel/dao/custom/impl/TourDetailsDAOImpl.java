package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.TourDetailsDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.dto.TourDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TourDetailsDAOImpl implements TourDetailsDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM tourDetail WHERE bookingId = ?", id);
    }

    @Override
    public boolean update(TourDetailDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE tourDetail SET tourId = ?, date = ?, amount = ? WHERE bookingId = ?", dto.getTourId(), dto.getDate(), dto.getAmount(), dto.getBookingId());
    }

    @Override
    public boolean add(TourDetailDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO tourDetail(bookingId, tourId, date, amount) VALUES(?, ?, ?, ?)", dto.getBookingId(), dto.getTourId(), dto.getDate(), dto.getAmount());
    }

    @Override
    public TourDetailDTO search(String id) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<TourDetailDTO> getAll() throws SQLException {
        ArrayList<TourDetailDTO> allTourDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM tourDetail");
        while (rst.next()) {
            allTourDetails.add(new TourDetailDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allTourDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}
