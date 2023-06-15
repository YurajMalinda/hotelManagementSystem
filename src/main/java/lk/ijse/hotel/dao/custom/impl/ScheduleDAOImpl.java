package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.ScheduleDAO;
import lk.ijse.hotel.dto.ScheduleDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;


public class ScheduleDAOImpl implements ScheduleDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM schedule WHERE scheduleId = ?", id);
    }

    @Override
    public boolean update(ScheduleDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE schedule SET scheduleDetails = ? WHERE scheduleId = ?", dto.getDetails(), dto.getId());
    }

    @Override
    public boolean add(ScheduleDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO schedule(scheduleId, scheduleDetails) VALUES(?, ?)", dto.getId(), dto.getDetails());
    }

    @Override
    public ScheduleDTO search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM schedule WHERE scheduleId = ?", id);
        if(rst.next()) {
            return new ScheduleDTO(rst.getString(1), rst.getString(2));
        }
        return null;    }

    @Override
    public ArrayList<ScheduleDTO> getAll() throws SQLException {
        ArrayList<ScheduleDTO> allScheduleDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM schedule");
        while (rst.next()) {
            allScheduleDetails.add(new ScheduleDTO(rst.getString(1), rst.getString(2)));
        }
        return allScheduleDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT scheduleId FROM schedule ORDER BY scheduleId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("scheduleId");
            int newscheduleId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newscheduleId);
        } else {
            return "S00-001";
        }
    }
}
