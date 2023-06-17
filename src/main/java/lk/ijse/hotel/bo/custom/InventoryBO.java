package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.bo.SuperBO;
import lk.ijse.hotel.dto.InventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {
    public boolean deleteItem(String id) throws SQLException;
    public boolean updateItem(InventoryDTO dto) throws SQLException;
    public boolean addItem(InventoryDTO dto) throws SQLException;
    public InventoryDTO searchItem(String id) throws SQLException;
    public ArrayList<InventoryDTO> getAllItems() throws SQLException;
    public String generateNewItemID() throws SQLException, ClassNotFoundException;
}
