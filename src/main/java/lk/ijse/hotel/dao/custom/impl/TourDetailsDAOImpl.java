package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.TourDetailsDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.TourDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TourDetailsDAOImpl implements TourDetailsDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM tourDetail WHERE bookingId = ?", id);
    }

    @Override
    public boolean update(TourDetail entity) throws SQLException {
        return SQLUtil.execute("UPDATE tourDetail SET tourId = ?, date = ?, amount = ? WHERE bookingId = ?", entity.getTourId(), entity.getDate(), entity.getAmount(), entity.getBookingId());
    }

    @Override
    public boolean add(TourDetail entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO tourDetail(bookingId, tourId, date, amount) VALUES(?, ?, ?, ?)", entity.getBookingId(), entity.getTourId(), entity.getDate(), entity.getAmount());
    }

    @Override
    public TourDetail search(String id) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<TourDetail> getAll() throws SQLException {
        ArrayList<TourDetail> allTourDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM tourDetail");
        while (rst.next()) {
            allTourDetails.add(new TourDetail(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allTourDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}
