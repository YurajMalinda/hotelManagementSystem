package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Schedule;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel {
    public static List<Schedule> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM schedule";
        List<Schedule> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();

        while (resultSet.next()) {
            data.add(new Schedule(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM schedule WHERE scheduleId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(String id, String details) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE schedule SET scheduleDetails = ? WHERE scheduleId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, details);
        pstm.setString(2, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule(scheduleId, scheduleDetails) " +
                "VALUES(?, ?)";
        return CrudUtil.execute(
                sql,
                schedule.getId(),
                schedule.getDetails());
    }

    public static Schedule search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Schedule(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }
}
