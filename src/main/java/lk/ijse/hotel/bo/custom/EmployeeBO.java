package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.bo.SuperBO;
import lk.ijse.hotel.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public boolean deleteEmployee(String id) throws SQLException;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException;

    public boolean addEmployee(EmployeeDTO dto) throws SQLException;

    public EmployeeDTO searchEmployee(String id) throws SQLException;

    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException;

    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException;
}
