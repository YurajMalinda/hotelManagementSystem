package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.EmployeeDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE empId = ?", id);
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("UPDATE employee SET empName = ?, gender = ?, email = ?, nic = ?, address = ? WHERE empId = ?", entity.getEmpName(), entity.getGender(), entity.getEmail(), entity.getNic(), entity.getAddress(), entity.getEmpId());
    }

    @Override
    public boolean add(Employee entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO employee(userId, empId, empName, gender, email, nic, address) VALUES(?, ?, ?, ?, ?, ?, ?)" , entity.getUserId(), entity.getEmpId(), entity.getEmpName(), entity.getGender(), entity.getEmail(), entity.getNic(), entity.getAddress());
    }

    @Override
    public Employee search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE empId = ?", id);
        if (rst.next()) {
            return new Employee(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> allEmployeeDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        while (rst.next()) {
            allEmployeeDetails.add(new Employee(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return allEmployeeDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("empId");
            int newEmployeeId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newEmployeeId);
        } else {
            return "E00-001";
        }
    }
}
