package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.TourBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.TourDAO;
import lk.ijse.hotel.dto.TourDTO;
import lk.ijse.hotel.entity.Tour;

import java.sql.SQLException;
import java.util.ArrayList;

public class TourBOImpl implements TourBO {
    TourDAO tourDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TOUR);
    @Override
    public boolean deleteTour(String id) throws SQLException {
        return tourDAO.delete(id);
    }

    @Override
    public boolean updateTour(TourDTO dto) throws SQLException {
        return tourDAO.update(new Tour(dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public boolean addTour(TourDTO dto) throws SQLException {
        return tourDAO.add(new Tour(dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public TourDTO searchTour(String id) throws SQLException {
        Tour t = tourDAO.search(id);
        return new TourDTO(t.getTourId(), t.getTourName(), t.getTourDetails(), t.getPrice());
    }
    @Override
    public ArrayList<TourDTO> getAllTours() throws SQLException {
        ArrayList<Tour> all = tourDAO.getAll();
        ArrayList<TourDTO> allTourDetails = new ArrayList<>();
        for(Tour t : all){
            allTourDetails.add(new TourDTO(t.getTourId(), t.getTourName(), t.getTourDetails(), t.getPrice()));
        }
        return allTourDetails;
    }

    @Override
    public String generateNewTourID() throws SQLException, ClassNotFoundException {
        return tourDAO.generateNewID();
    }
}
