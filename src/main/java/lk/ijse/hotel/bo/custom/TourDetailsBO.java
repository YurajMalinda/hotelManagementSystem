package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.TourDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TourDetailsBO {
    public boolean deleteTourDetail(String id) throws SQLException;
    public boolean updateTourDetail(TourDetailDTO dto) throws SQLException;
    public boolean addTourDetail(TourDetailDTO dto) throws SQLException;
    public ArrayList<TourDetailDTO> getAllTourDetails() throws SQLException;
}
