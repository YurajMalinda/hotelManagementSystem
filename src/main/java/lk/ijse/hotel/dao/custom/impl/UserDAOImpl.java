package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.UserDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.User;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM user WHERE userId = ?", id);
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return SQLUtil.execute("UPDATE user SET userName = ?, password = ?, title = ? WHERE userId = ?", entity.getUserName(), entity.getPassword(), entity.getTitle(), entity.getUserId());
    }

    @Override
    public boolean add(User entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO User(userId, userName, password, title) VALUES(?, ?, ?, ?)", entity.getUserId(), entity.getUserName(), entity.getPassword(), entity.getTitle());
    }

    @Override
    public User search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE userId = ?", id);
        if(rst.next()) {
            return new User(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ArrayList<User> allUserDetails = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM user");
        while (rst.next()) {
            allUserDetails.add(new User(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allUserDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}


