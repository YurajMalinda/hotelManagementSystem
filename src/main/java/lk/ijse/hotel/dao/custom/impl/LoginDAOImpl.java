package lk.ijse.hotel.dao.custom.impl;

import lk.ijse.hotel.dao.custom.LoginDAO;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.entity.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean update(Login dto) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean add(Login dto) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public Login search(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<Login> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM login");
        ArrayList<Login> allLoginDetails = new ArrayList<>();
        while (rst.next()) {
            allLoginDetails.add(new Login(rst.getString(1), rst.getString(2), rst.getString(3)));
        }
        return allLoginDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}
