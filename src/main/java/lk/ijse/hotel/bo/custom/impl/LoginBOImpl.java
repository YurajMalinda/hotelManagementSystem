package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.LoginBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.LoginDAO;
import lk.ijse.hotel.dto.LoginDTO;
import lk.ijse.hotel.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.LOGIN);
    @Override
    public ArrayList<LoginDTO> getAllLogins() throws SQLException {
        ArrayList<Login> all = loginDAO.getAll();
        ArrayList<LoginDTO> allLoginDetails = new ArrayList<>();
        for(Login l : all){
            allLoginDetails.add(new LoginDTO(l.getUserName(), l.getPassWord(), l.getTitle()));
        }
        return allLoginDetails;
    }
}
