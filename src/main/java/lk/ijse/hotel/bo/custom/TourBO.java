package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.TourDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TourBO {
    public boolean deleteTour(String id) throws SQLException;
    public boolean updateTour(TourDTO dto) throws SQLException;
    public boolean addTour(TourDTO dto) throws SQLException;
    public TourDTO searchTour(String id) throws SQLException;
    public ArrayList<TourDTO> getAllTours() throws SQLException;
    public String generateNewTourID() throws SQLException, ClassNotFoundException;
}
