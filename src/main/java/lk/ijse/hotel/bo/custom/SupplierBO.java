package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO {
    public boolean deleteSupplier(String id) throws SQLException;
    public boolean updateSupplier(SupplierDTO dto) throws SQLException;
    public boolean addSupplier(SupplierDTO dto) throws SQLException;
    public SupplierDTO searchSupplier(String id) throws SQLException;
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException;
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException;
}
