package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.ScheduleDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Schedule;

import java.sql.*;
import java.util.ArrayList;


public class ScheduleDAOImpl implements ScheduleDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM schedule WHERE scheduleId = ?", id);
    }

    @Override
    public boolean update(Schedule entity) throws SQLException {
        return SQLUtil.execute("UPDATE schedule SET scheduleDetails = ? WHERE scheduleId = ?", entity.getScheduleDetails(), entity.getScheduleId());
    }

    @Override
    public boolean add(Schedule entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO schedule(scheduleId, scheduleDetails) VALUES(?, ?)", entity.getScheduleId(), entity.getScheduleDetails());
    }

    @Override
    public Schedule search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM schedule WHERE scheduleId = ?", id);
        if(rst.next()) {
            return new Schedule(rst.getString(1), rst.getString(2));
        }
        return null;    }

    @Override
    public ArrayList<Schedule> getAll() throws SQLException {
        ArrayList<Schedule> allScheduleDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM schedule");
        while (rst.next()) {
            allScheduleDetails.add(new Schedule(rst.getString(1), rst.getString(2)));
        }
        return allScheduleDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT scheduleId FROM schedule ORDER BY scheduleId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("scheduleId");
            int newScheduleId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newScheduleId);
        } else {
            return "S00-001";
        }
    }
}
