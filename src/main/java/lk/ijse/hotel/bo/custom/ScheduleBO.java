package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.ScheduleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ScheduleBO {
    public boolean deleteSchedule(String id) throws SQLException;
    public boolean updateSchedule(ScheduleDTO dto) throws SQLException;
    public boolean addSchedule(ScheduleDTO dto) throws SQLException;
    public ScheduleDTO searchSchedule(String id) throws SQLException;
    public ArrayList<ScheduleDTO> getAllSchedules() throws SQLException;
    public String generateNewScheduleID() throws SQLException, ClassNotFoundException;
}
