package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO {
    public boolean deleteUser(String id) throws SQLException;
    public boolean updateUser(UserDTO dto) throws SQLException;
    public boolean addUser(UserDTO dto) throws SQLException;
    public UserDTO searchUser(String id) throws SQLException;
    public ArrayList<UserDTO> getAllUsers() throws SQLException;
}
