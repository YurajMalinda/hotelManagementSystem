package lk.ijse.hotel.dao;

import lk.ijse.hotel.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }

    public static DAOFactory getDAOFactory(){
        return (daoFactory==null)? daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        BOOKING, EMPLOYEE, FOOD, GUEST, INVENTORY, LOGIN, ORDER, ORDER_DETAILS, PAYMENT, QUERY, ROOM, SCHEDULE, SUPPLIER, TOUR, TOUR_DETAILS, USER
    }

    public <T extends SuperDAO> T getDAO(DAOTypes res){
        switch (res){
            case BOOKING:
                return (T) new BookingDAOImpl();
            case EMPLOYEE:
                return (T) new EmployeeDAOImpl();
            case FOOD:
                return (T) new FoodDAOImpl();
            case GUEST:
                return (T) new GuestDAOImpl();
            case INVENTORY:
                return (T) new InventoryDAOImpl();
            case LOGIN:
                return (T) new LoginDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailsDAOImpl();
            case PAYMENT:
                return (T) new PaymentDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            case ROOM:
                return (T) new RoomDAOImpl();
            case SCHEDULE:
                return (T) new ScheduleDAOImpl();
            case SUPPLIER:
                return (T) new SupplierDAOImpl();
            case TOUR:
                return (T) new TourDAOImpl();
            case TOUR_DETAILS:
                return (T) new TourDetailsDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            default:
                return null;
        }
    }
}
