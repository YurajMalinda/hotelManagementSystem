package lk.ijse.hotel.dao;

import lk.ijse.hotel.entity.Tour;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> extends SuperDAO{
    boolean delete(ID id) throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean add(T entity) throws SQLException;

    T search(ID id) throws SQLException;

    ArrayList<T> getAll() throws SQLException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
