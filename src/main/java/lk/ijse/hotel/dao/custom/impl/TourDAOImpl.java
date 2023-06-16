package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.TourDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Tour;

import java.sql.*;
import java.util.ArrayList;

public class TourDAOImpl implements TourDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM tour WHERE tourId = ?", id);
    }

    @Override
    public boolean update(Tour entity) throws SQLException {
        return SQLUtil.execute("UPDATE tour SET tourName = ?, tourDetails = ?, price = ? WHERE tourId = ?", entity.getTourName(), entity.getTourDetails(), entity.getPrice(), entity.getTourId());
    }

    @Override
    public boolean add(Tour entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO tour(tourId, tourName, tourDetails, Price) VALUES(?, ?, ?, ?)", entity.getTourId(), entity.getTourName(), entity.getTourDetails(), entity.getPrice());
    }

    @Override
    public Tour search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM tour WHERE tourId = ?", id);
        if(rst.next()) {
            return new Tour(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
        }
        return null;    }

    @Override
    public ArrayList<Tour> getAll() throws SQLException {
        ArrayList<Tour> allTourDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM tour");
        while (rst.next()) {
            allTourDetails.add(new Tour(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allTourDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT tourId FROM tour ORDER BY tourId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("tourId");
            int newTourId = Integer.parseInt(id.replace("T00-", "")) + 1;
            return String.format("T00-%03d", newTourId);
        } else {
            return "T00-001";
        }
    }
}
