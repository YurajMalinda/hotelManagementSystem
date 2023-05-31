package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.SupplierDTO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl {
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

    public static boolean update(SupplierDTO supplierDTO) throws SQLException {
        String sql = "UPDATE supplier SET supName = ?, supContact = ?, supplyDetail = ? WHERE supId = ?";

        return SQLUtil.execute(
                sql,
                supplierDTO.getName(),
                supplierDTO.getContact(),
                supplierDTO.getDetails(),
                supplierDTO.getId());
    }
    public static boolean add(SupplierDTO supplierDTO) throws SQLException {
        String sql = "INSERT INTO supplier(supId, supName, supContact, supplyDetail) " +
                "VALUES(?, ?, ?, ?)";
        return SQLUtil.execute(
                sql,
                supplierDTO.getId(),
                supplierDTO.getName(),
                supplierDTO.getContact(),
                supplierDTO.getDetails());
    }

    public static SupplierDTO search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE supId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new SupplierDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static List<SupplierDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";
        List<SupplierDTO> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new SupplierDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }
}
