package lk.ijse.hotel.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderDTO {
    private String orderID;
    private String date;
    private String bookingID;

    List<OrderDetailsDTO> orderDetails;

    public OrderDTO(String orderID, LocalDate date, String bookingID) {
        this.orderID = orderID;
        this.date = String.valueOf(date);
        this.bookingID = bookingID;
        this.orderDetails = orderDetails;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderID='" + orderID + '\'' +
                ", date='" + date + '\'' +
                ", bookingID='" + bookingID + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
