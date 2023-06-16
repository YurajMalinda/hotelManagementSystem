package lk.ijse.hotel.dao.custom.impl;


import lk.ijse.hotel.dao.custom.SupplierDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Supplier;

import java.sql.*;
import java.util.ArrayList;


public class SupplierDAOImpl implements SupplierDAO{
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supId = ?", id);
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute("UPDATE supplier SET supName = ?, supContact = ?, supplyDetail = ? WHERE supId = ?", entity.getSupName(), entity.getSupContact(), entity.getSuppyDetail(), entity.getSupId());
    }

    @Override
    public boolean add(Supplier entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO supplier(supId, supName, supContact, supplyDetail) VALUES(?, ?, ?, ?)", entity.getSupId(), entity.getSupName(), entity.getSupContact(), entity.getSuppyDetail());
    }

    @Override
    public Supplier search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE supId = ?", id);
        if(rst.next()) {
            return new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> allSupplierDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier");
        while (rst.next()) {
            allSupplierDetails.add(new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allSupplierDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supId FROM supplier ORDER BY supId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("supId");
            int newSupId = Integer.parseInt(id.replace("SP0-", "")) + 1;
            return String.format("SP0-%03d", newSupId);
        } else {
            return "SP0-001";
        }
    }
}
