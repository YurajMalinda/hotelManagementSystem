package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.TourDetailsBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.TourDetailsDAO;
import lk.ijse.hotel.dto.TourDetailDTO;
import lk.ijse.hotel.entity.TourDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class TourDetailsBOImpl implements TourDetailsBO {
    TourDetailsDAO tourDetailsDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TOUR_DETAILS);
    @Override
    public boolean deleteTourDetail(String id) throws SQLException {
        return tourDetailsDAO.delete(id);
    }

    @Override
    public boolean updateTourDetail(TourDetailDTO dto) throws SQLException {
        return tourDetailsDAO.update(new TourDetail(dto.getBookingId(), dto.getTourId(), dto.getAmount(), dto.getDate()));
    }

    @Override
    public boolean addTourDetail(TourDetailDTO dto) throws SQLException {
        return tourDetailsDAO.add(new TourDetail(dto.getBookingId(), dto.getTourId(), dto.getAmount(), dto.getDate()));
    }

    @Override
    public ArrayList<TourDetailDTO> getAllTourDetails() throws SQLException {
        ArrayList<TourDetail> all = tourDetailsDAO.getAll();
        ArrayList<TourDetailDTO> allTourDetails = new ArrayList<>();
        for(TourDetail t : all){
            allTourDetails.add(new TourDetailDTO(t.getBookingId(), t.getTourId(), t.getAmount(), t.getDate()));
        }
        return allTourDetails;   
    }
}
