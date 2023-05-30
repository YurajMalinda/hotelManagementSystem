package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.EmployeeDTO;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM employee WHERE empId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(String id, String name, String gender, String email, String nic, String address) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET empName = ?, gender = ?, email = ?, nic = ?, address = ? WHERE empId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, gender);
        pstm.setString(3, email);
        pstm.setString(4, nic);
        pstm.setString(5, address);
        pstm.setString(6, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(EmployeeDTO employeeDTO) throws SQLException {
        String sql = "INSERT INTO employee(userId, empId, empName, gender, email, nic, address) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                employeeDTO.getUserId(),
                employeeDTO.getId(),
                employeeDTO.getName(),
                employeeDTO.getGender(),
                employeeDTO.getEmail(),
                employeeDTO.getNic(),
                employeeDTO.getAddress());
    }



    public static EmployeeDTO search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee WHERE empId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new EmployeeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

    public static List<EmployeeDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";
        List<EmployeeDTO> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new EmployeeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }
}
