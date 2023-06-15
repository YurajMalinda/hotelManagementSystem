package lk.ijse.hotel.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> {
    boolean delete(ID id) throws SQLException;

    boolean update(T dto) throws SQLException;

    boolean add(T dto) throws SQLException;

    T search(ID id) throws SQLException;

    ArrayList<T> getAll() throws SQLException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
