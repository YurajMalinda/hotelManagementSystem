package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.ScheduleBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.ScheduleDAO;
import lk.ijse.hotel.dto.ScheduleDTO;
import lk.ijse.hotel.entity.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleBOImpl implements ScheduleBO {
    ScheduleDAO scheduleDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SCHEDULE);
    @Override
    public boolean deleteSchedule(String id) throws SQLException {
        return scheduleDAO.delete(id);
    }

    @Override
    public boolean updateSchedule(ScheduleDTO dto) throws SQLException {
        return scheduleDAO.update(new Schedule(dto.getDetails(), dto.getId()));
    }

    @Override
    public boolean addSchedule(ScheduleDTO dto) throws SQLException {
        return scheduleDAO.add(new Schedule(dto.getId(), dto.getDetails()));
    }

    @Override
    public ScheduleDTO searchSchedule(String id) throws SQLException {
        Schedule s = scheduleDAO.search(id);
        return new ScheduleDTO(s.getScheduleId(), s.getScheduleDetails());
    }

    @Override
    public ArrayList<ScheduleDTO> getAllSchedules() throws SQLException {
        ArrayList<Schedule> all = scheduleDAO.getAll();
        ArrayList<ScheduleDTO> allScheduleDetails = new ArrayList<>();
        for(Schedule s : all){
            allScheduleDetails.add(new ScheduleDTO(s.getScheduleId(), s.getScheduleDetails()));
        }
        return allScheduleDetails;
    }

    @Override
    public String generateNewScheduleID() throws SQLException, ClassNotFoundException {
        return scheduleDAO.generateNewID();
    }
}
