package lk.ijse.hotel.bo;

import lk.ijse.hotel.bo.custom.impl.*;
import lk.ijse.hotel.dao.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        return (boFactory==null)? boFactory=new BOFactory():boFactory;
    }

    public enum BOTypes{
        BOOKING_BO, EMPLOYEE_BO, FOOD_BO, GUEST_BO, INVENTORY_BO, LOGIN_BO, ORDER_BO, PAYMENT_BO, ROOM_BO, SCHEDULE_BO, SUPPLIER_BO, TOUR_BO, TOUR_DETAILS_BO, USER_BO
    }

    public <T extends SuperBO> T getBO(BOFactory.BOTypes res){
        switch (res){
            case BOOKING_BO:
                return (T) new BookingBOImpl();
            case EMPLOYEE_BO:
                return (T) new EmployeeBOImpl();
            case FOOD_BO:
                return (T) new FoodBOImpl();
            case GUEST_BO:
                return (T) new GuestBOImpl();
            case INVENTORY_BO:
                return (T) new InventoryBOImpl();
            case LOGIN_BO:
                return (T) new LoginBOImpl();
            case ORDER_BO:
                return (T) new OrderBOImpl();
            case PAYMENT_BO:
                return (T) new PaymentBOImpl();
            case ROOM_BO:
                return (T) new RoomBOImpl();
            case SCHEDULE_BO:
                return (T) new ScheduleBOImpl();
            case SUPPLIER_BO:
                return (T) new SupplierBOImpl();
            case TOUR_BO:
                return (T) new TourBOImpl();
            case TOUR_DETAILS_BO:
                return (T) new TourDetailsBOImpl();
            case USER_BO:
                return (T) new UserBOImpl();
            default:
                return null;
        }
    }
}
