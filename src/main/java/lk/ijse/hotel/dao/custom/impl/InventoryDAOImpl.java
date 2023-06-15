package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.InventoryDAO;
import lk.ijse.hotel.dto.InventoryDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM inventory WHERE itemId = ?", id);
    }

    @Override
    public boolean update(InventoryDTO dto) throws SQLException {
        return SQLUtil.execute("UPDATE inventory SET itemName = ?, itemDetails = ?, itemPrice = ? WHERE itemId = ?", dto.getName(), dto.getDetails(), dto.getPrice(), dto.getId());
    }

    @Override
    public boolean add(InventoryDTO dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO inventory(itemId, itemName, itemDetails, itemPrice) VALUES(?, ?, ?, ?)", dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice());
    }

    @Override
    public InventoryDTO search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory WHERE itemId = ?", id);
        if(rst.next()) {
            return new InventoryDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
        }
        return null;
    }

    @Override
    public ArrayList<InventoryDTO> getAll() throws SQLException {
        ArrayList<InventoryDTO> allInventoryDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory");
        while (rst.next()) {
            allInventoryDetails.add(new InventoryDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allInventoryDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT itemId FROM inventory ORDER BY itemId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newInventoryId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newInventoryId);
        } else {
            return "I00-001";
        }
    }
}
