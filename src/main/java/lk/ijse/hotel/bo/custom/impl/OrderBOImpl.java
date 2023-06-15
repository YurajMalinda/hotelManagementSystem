package lk.ijse.hotel.bo.custom.impl;

import lk.ijse.hotel.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.hotel.dao.custom.impl.util.SQLUtil;
import lk.ijse.hotel.db.DBConnection;
import lk.ijse.hotel.dto.OrderDTO;
import lk.ijse.hotel.dto.OrderDetailsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl {
    public static boolean placeOrder(List<OrderDetailsDTO> cartDTOList, String oId, String cusId) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = OrderBOImpl.saveFoodOrder(oId, LocalDate.now(),cusId);
            if(isSaved) {
                boolean isUpdated = OrderBOImpl.saveOrderDetails(cartDTOList);
                if(isUpdated) {
                    con.commit();
                    return true;
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

    public static boolean saveOrderDetails(List<OrderDetailsDTO> cartDTOList) throws SQLException {
        for(OrderDetailsDTO orderDetailsDTO : cartDTOList) {
            if(!OrderDetailsDAOImpl.add(orderDetailsDTO)) {
                return false;
            }
        }
        return true;
    }
    public static boolean saveOrder(OrderDTO orderDTO) {
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            /*if order id already exist*/
            if (orderDAO.exist(dto.getOrderId())) {
                return false;
            }

            connection.setAutoCommit(false);

            Orders orderEntity = new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getCustomerId());
            boolean orderAdded = orderDAO.save(orderEntity);
            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO odDTO : dto.getOrderDetaisList()) {
                OrderDetails orderDetailsEntity = new OrderDetails(odDTO.getOrderID(), odDTO.getItemCode(), odDTO.getQty(), odDTO.getUnitPrice());
                boolean odAdded = orderDetailsDAO.save(orderDetailsEntity);
                if (!odAdded) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
                ItemDTO item = findItemByID(orderDetailsEntity.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetailsEntity.getQty());
                boolean itemUpdate = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));

                if (!itemUpdate) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
