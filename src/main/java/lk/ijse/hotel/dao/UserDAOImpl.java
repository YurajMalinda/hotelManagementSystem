package lk.ijse.hotel.dao;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.UserDTO;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl {
    public static boolean delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM user WHERE userId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }


    public static boolean update(String id, String name, String password, String title) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE user SET userName = ?, password = ?, title = ? WHERE userId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, password);
        pstm.setString(3, title);
        pstm.setString(4, id);

        return pstm.executeUpdate() > 0;
    }


    public static boolean add(UserDTO userDTO) throws SQLException {
        String sql = "INSERT INTO User(userId, userName, password, title) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getTitle());
    }

    public static UserDTO search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE userId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new UserDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

                    );
        }
        return null;
    }

    public static List<UserDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";
        List<UserDTO> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new UserDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

                    ));
        }
        return data;
    }
}


