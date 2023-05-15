package lk.ijse.hotel.model;

import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.User;
import lk.ijse.hotel.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
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


    public static boolean add(User user) throws SQLException {
        String sql = "INSERT INTO User(userId, userName, password, title) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getTitle());
    }

    public static User search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE userId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

                    );
        }
        return null;
    }

    public static List<User> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";
        List<User> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

                    ));
        }
        return data;
    }
}


