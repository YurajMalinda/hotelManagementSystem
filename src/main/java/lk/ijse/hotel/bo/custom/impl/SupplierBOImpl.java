package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.SupplierBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.SupplierDAO;
import lk.ijse.hotel.dto.SupplierDTO;
import lk.ijse.hotel.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        return supplierDAO.delete(id);
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.update(new Supplier(dto.getName(), dto.getContact(), dto.getDetails(), dto.getId()));
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.add(new Supplier(dto.getId(), dto.getName(), dto.getContact(), dto.getDetails()));
    }

    @Override
    public SupplierDTO searchSupplier(String id) throws SQLException {
        Supplier s = supplierDAO.search(id);
        return new SupplierDTO(s.getSupId(), s.getSupName(), s.getSupContact(), s.getSuppyDetail());
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ArrayList<Supplier> all = supplierDAO.getAll();
        ArrayList<SupplierDTO> allSupplierDetails = new ArrayList<>();
        for(Supplier s : all){
            allSupplierDetails.add(new SupplierDTO(s.getSupId(), s.getSupName(), s.getSupContact(), s.getSuppyDetail()));
        }
        return allSupplierDetails;
    }

    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }
}
