package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.EmployeeBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.EmployeeDAO;
import lk.ijse.hotel.dto.EmployeeDTO;
import lk.ijse.hotel.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean deleteEmployee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getName(), dto.getGender(), dto.getEmail(), dto.getNic(), dto.getAddress(), dto.getId()));
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.add(new Employee( dto.getUserId(), dto.getId(), dto.getName(), dto.getGender(), dto.getEmail(), dto.getNic(), dto.getAddress()));
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException {
        Employee e = employeeDAO.search(id);
        return new EmployeeDTO(e.getEmpId(), e.getEmpName(), e.getGender(), e.getAddress(), e.getEmail(), e.getNic());
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ArrayList<Employee> all = employeeDAO.getAll();
        ArrayList<EmployeeDTO> allEmployeeDetails = new ArrayList<>();
        for(Employee b: all){
            allEmployeeDetails.add(new EmployeeDTO(b.getEmpId(), b.getEmpName(), b.getGender(), b.getAddress(), b.getEmail(), b.getNic()));
        }
        return allEmployeeDetails;
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }

}
