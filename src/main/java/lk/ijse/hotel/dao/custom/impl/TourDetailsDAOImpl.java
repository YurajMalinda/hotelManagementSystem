package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.view.tdm.TourDetail;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourDetailsDAOImpl {

    public static List<TourDetail> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tourDetail";
        List<TourDetail> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();

        while (resultSet.next()) {
            data.add(new TourDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM tourDetail WHERE bookingId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }


    public static boolean update(TourDetail tourDetail) throws SQLException {
        String sql = "UPDATE tourDetail SET tourId = ?, date = ?, amount = ? WHERE bookingId = ?";
        return SQLUtil.execute(
                sql,
                tourDetail.getTourId(),
                tourDetail.getDate(),
                tourDetail.getAmount(),
                tourDetail.getBookingId());
    }

    public static boolean add(TourDetail tourDetail) throws SQLException {
        String sql = "INSERT INTO tourDetail(bookingId, tourId, date, amount) " +
                "VALUES(?, ?, ?, ?)";
        return SQLUtil.execute(
                sql,
                tourDetail.getBookingId(),
                tourDetail.getTourId(),
                tourDetail.getDate(),
                tourDetail.getAmount());
    }


}
