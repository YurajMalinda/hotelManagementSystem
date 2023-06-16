package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.FoodBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.FoodDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.dto.FoodDTO;
import lk.ijse.hotel.entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodBOImpl implements FoodBO {
    FoodDAO foodDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.FOOD);
    @Override
    public boolean deleteFood(String id) throws SQLException {
        return foodDAO.delete(id);
    }

    @Override
    public boolean updateFood(FoodDTO dto) throws SQLException {
        return foodDAO.update(new Food(dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public boolean addFood(FoodDTO dto) throws SQLException {
        return foodDAO.add(new Food(dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public FoodDTO searchFood(String id) throws SQLException {
        Food f = foodDAO.search(id);
        return new FoodDTO(f.getFoodId(), f.getFoodName(), f.getFoodDetails(), f.getFoodPrice());
    }

    @Override
    public ArrayList<FoodDTO> getAllFoods() throws SQLException {
        ArrayList<Food> all = foodDAO.getAll();
        ArrayList<FoodDTO> allFoodDetails = new ArrayList<>();
        for(Food f : all){
            allFoodDetails.add(new FoodDTO(f.getFoodId(), f.getFoodName(), f.getFoodDetails(), f.getFoodPrice()));
        }
        return allFoodDetails;
    }

    @Override
    public String generateNewFoodID() throws SQLException, ClassNotFoundException {
        return foodDAO.generateNewID();
    }
}
