package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.bo.SuperBO;
import lk.ijse.hotel.dto.FoodDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FoodBO extends SuperBO {
    public boolean deleteFood(String id) throws SQLException;

    public boolean updateFood(FoodDTO dto) throws SQLException;

    public boolean addFood(FoodDTO dto) throws SQLException;

    public FoodDTO searchFood(String id) throws SQLException;

    public ArrayList<FoodDTO> getAllFoods() throws SQLException;

    public String generateNewFoodID() throws SQLException, ClassNotFoundException;
}
