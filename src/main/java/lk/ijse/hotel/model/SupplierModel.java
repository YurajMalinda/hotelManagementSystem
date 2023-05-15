package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.Room;
import lk.ijse.hotel.dto.Supplier;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM supplier WHERE supId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;

    }

//    public static boolean update(String id, String name, String contact, String details) throws SQLException {
//        Connection con = DBConnection.getInstance().getConnection();
//        String sql = "UPDATE supplier SET supName = ?, supContact = ?, supplyDetail = ? WHERE supId = ?";
//        PreparedStatement pstm = con.prepareStatement(sql);
//        pstm.setString(1, name);
//        pstm.setString(2, contact);
//        pstm.setString(3, details);
//        pstm.setString(4, id);
//
//        return pstm.executeUpdate() > 0;
//    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET supName = ?, supContact = ?, supplyDetail = ? WHERE supId = ?";

        return CrudUtil.execute(
                sql,
                supplier.getName(),
                supplier.getContact(),
                supplier.getDetails(),
                supplier.getId());
    }
    public static boolean add(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier(supId, supName, supContact, supplyDetail) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getDetails());
    }

    public static Supplier search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE supId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static List<Supplier> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";
        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }
}
