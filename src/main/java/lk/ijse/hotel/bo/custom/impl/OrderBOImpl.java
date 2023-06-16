package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.bo.custom.OrderBO;
import lk.ijse.hotel.dao.DAOFactory;
import lk.ijse.hotel.dao.custom.OrderDAO;
import lk.ijse.hotel.dao.custom.OrderDetailsDAO;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.OrderDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;
import lk.ijse.hotel.entity.FoodOrderDetails;
import lk.ijse.hotel.entity.FoodOrders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean saveOrder(OrderDTO dto) {
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            FoodOrders orderEntity = new FoodOrders(dto.getOrderID(), dto.getDate(), dto.getBookingID());
            boolean orderAdded = orderDAO.add(orderEntity);
            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailsDTO odDTO : dto.getOrderDetails()) {
                 FoodOrderDetails orderDetailsEntity = new FoodOrderDetails(odDTO.getOrderID(), odDTO.getMealID(), odDTO.getQty(), odDTO.getTotal(), odDTO.getDate());
                boolean odAdded = orderDetailsDAO.add(orderDetailsEntity);
                if (!odAdded) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
//                ItemDTO item = findItemByID(orderDetailsEntity.getItemCode());
//                item.setQtyOnHand(item.getQtyOnHand() - orderDetailsEntity.getQty());
//                boolean itemUpdate = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));
//
//                if (!itemUpdate) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    return false;
//                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<OrderDTO> getAllFoodOrders() throws SQLException {
        ArrayList<FoodOrders> all = orderDAO.getAll();
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        for(FoodOrders f : all){
            allOrders.add(new OrderDTO(f.getOrderId(), f.getDate(), f.getBookingId()));
        }
        return allOrders;
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllFoodOrderDetails() throws SQLException {
        ArrayList<FoodOrderDetails> all = orderDetailsDAO.getAll();
        ArrayList<OrderDetailsDTO> allOrderDetails = new ArrayList<>();
        for(FoodOrderDetails f : all){
            allOrderDetails.add(new OrderDetailsDTO(f.getOrderId(), f.getFoodId(), f.getQty(), f.getAmount(), f.getDate()));
        }
        return allOrderDetails;
    }

//    public static boolean placeOrder(List<OrderDetailsDTO> cartDTOList, String oId, String cusId) throws SQLException {
//        Connection con = null;
//        try {
//            con = DBConnection.getInstance().getConnection();
//            con.setAutoCommit(false);
//
//            boolean isSaved = OrderBOImpl.saveFoodOrder(oId, LocalDate.now(),cusId);
//            if(isSaved) {
//                boolean isUpdated = OrderBOImpl.saveOrderDetails(cartDTOList);
//                if(isUpdated) {
//                    con.commit();
//                    return true;
//                }
//            }
//            return false;
//        } catch (SQLException er) {
//            con.rollback();
//            return false;
//        } finally {
//            System.out.println("finally");
//            con.setAutoCommit(true);
//        }
//    }
//
//    public static boolean saveOrderDetails(List<OrderDetailsDTO> cartDTOList) throws SQLException {
//        for(OrderDetailsDTO orderDetailsDTO : cartDTOList) {
//            if(!OrderDetailsDAOImpl.add(orderDetailsDTO)) {
//                return false;
//            }
//        }
//        return true;
//    }





//    public boolean saveOrder(OrderDTO dto) {
//        /*Transaction*/
//        Connection connection = null;
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//
//            FoodOrders orderEntity = new FoodOrders(dto.getOrderID(), dto.getDate(), dto.getBookingID());
//            boolean orderAdded = orderDAO.add(orderEntity);
//            if (!orderAdded) {
//                connection.rollback();
//                connection.setAutoCommit(true);
//                return false;
//            }
//
//            for (OrderDetailsDTO odDTO : dto.getOrderDetails()) {
//                 FoodOrderDetails orderDetailsEntity = new FoodOrderDetails(odDTO.getOrderID(), odDTO.getMealID(), odDTO.getQty(), odDTO.getTotal(), odDTO.getDate());
//                boolean odAdded = orderDetailsDAO.add(orderDetailsEntity);
//                if (!odAdded) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    return false;
//                }
//
////                //Search & Update Item
////                ItemDTO item = findItemByID(orderDetailsEntity.getItemCode());
////                item.setQtyOnHand(item.getQtyOnHand() - orderDetailsEntity.getQty());
////                boolean itemUpdate = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));
////
////                if (!itemUpdate) {
////                    connection.rollback();
////                    connection.setAutoCommit(true);
////                    return false;
////                }
//            }
//            connection.commit();
//            connection.setAutoCommit(true);
//            return true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return false;
//    }
//
//    public ArrayList<FoodOrders> getAll() throws SQLException {
//        ResultSet rst = SQLUtil.execute("SELECT * FROM foodorders");
//        ArrayList<FoodOrders> allOrders = new ArrayList<>();
//        while (rst.next()) {
//            allOrders.add(new FoodOrders(rst.getString(1), rst.getString(2), rst.getString(3)));
//        }
//        return allOrders;    }
//
//    @Override
//    public String generateNewID() throws SQLException, ClassNotFoundException {
//        ResultSet rst = SQLUtil.execute("SELECT orderId FROM foodOrders ORDER BY orderId DESC LIMIT 1;");
//        if (rst.next()) {
//            String id = rst.getString("orderId");
//            int newOrderId = Integer.parseInt(id.replace("O00-", "")) + 1;
//            return String.format("O00-%03d", newOrderId);
//        } else {
//            return "O00-001";
//        }
//    }
//
//    @Override
//    public ArrayList<FoodOrderDetails> getAllFoodOrderDetails() throws SQLException {
//        ResultSet rst = SQLUtil.execute("SELECT * FROM foodorderDetails");
//        ArrayList<FoodOrderDetails> allOrderDetails = new ArrayList<>();
//        while (rst.next()) {
//            allOrderDetails.add(new FoodOrderDetails(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getString(5)));
//        }
//        return allOrderDetails;
//    }
}
