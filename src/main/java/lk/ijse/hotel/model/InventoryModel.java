package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Inventory;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM inventory WHERE itemId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(String id, String name, String details, Double price) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE inventory SET itemName = ?, itemDetails = ?, itemPrice = ? WHERE itemId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, details);
        pstm.setDouble(3, price);
        pstm.setString(4, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO inventory(itemId, itemName, itemDetails, itemPrice) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                inventory.getId(),
                inventory.getName(),
                inventory.getDetails(),
                inventory.getPrice());
    }

    public static Inventory search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM inventory WHERE itemId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Inventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static List<Inventory> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM inventory";
        List<Inventory> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Inventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return data;
    }
}
