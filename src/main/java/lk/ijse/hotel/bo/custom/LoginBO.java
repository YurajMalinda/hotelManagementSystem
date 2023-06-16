package lk.ijse.hotel.bo.custom;

import lk.ijse.hotel.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBO {
    public ArrayList<LoginDTO> getAllLogins() throws SQLException;
}
