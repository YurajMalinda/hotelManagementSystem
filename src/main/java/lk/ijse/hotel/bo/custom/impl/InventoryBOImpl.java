package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.InventoryBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.InventoryDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.dto.InventoryDTO;
import lk.ijse.hotel.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);
    @Override
    public boolean deleteItem(String id) throws SQLException {
        return inventoryDAO.delete(id);    
    }

    @Override
    public boolean updateItem(InventoryDTO dto) throws SQLException {
        return inventoryDAO.update(new Inventory(dto.getName(), dto.getDetails(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public boolean addItem(InventoryDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO inventory(itemId, itemName, itemDetails, itemPrice) VALUES(?, ?, ?, ?)", dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice());    }

    @Override
    public InventoryDTO searchItem(String id) throws SQLException {
        Inventory i = inventoryDAO.search(id);
        return new InventoryDTO(i.getItemId(), i.getItemName(), i.getItemDetails(), i.getItemPrice());
    }

    @Override
    public ArrayList<InventoryDTO> getAllItems() throws SQLException {
        ArrayList<Inventory> all = inventoryDAO.getAll();
        ArrayList<InventoryDTO> allInventoryDetails = new ArrayList<>();
        for(Inventory i : all){
            allInventoryDetails.add(new InventoryDTO(i.getItemId(), i.getItemName(), i.getItemDetails(), i.getItemPrice()));
        }
        return allInventoryDetails;
    }

    @Override
    public String generateNewItemID() throws SQLException, ClassNotFoundException {
        return inventoryDAO.generateNewID();
    }
}
