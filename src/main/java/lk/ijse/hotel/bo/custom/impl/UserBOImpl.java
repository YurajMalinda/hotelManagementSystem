package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.UserBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.UserDAO;
import lk.ijse.hotel.dto.UserDTO;
import lk.ijse.hotel.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean deleteUser(String id) throws SQLException {
        return userDAO.delete(id);
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException {
        return userDAO.update(new User(dto.getId(), dto.getName(), dto.getPassword(), dto.getTitle()));
    }

    @Override
    public boolean addUser(UserDTO dto) throws SQLException {
        return userDAO.add(new User(dto.getId(), dto.getName(), dto.getPassword(), dto.getTitle()));
    }

    @Override
    public UserDTO searchUser(String id) throws SQLException {
        User u = userDAO.search(id);
        return new UserDTO(u.getUserId(), u.getUserName(), u.getPassword(), u.getTitle());
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException {
        ArrayList<User> all = userDAO.getAll();
        ArrayList<UserDTO> allUserDetails = new ArrayList<>();
        for(User u : all){
            allUserDetails.add(new UserDTO(u.getUserId(), u.getUserName(), u.getPassword(), u.getTitle()));
        }
        return allUserDetails;
    }
}
