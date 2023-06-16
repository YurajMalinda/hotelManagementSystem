package lk.ijse.hotel.dto;

import lombok.*;

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

    public OrderDTO(String orderId, String date, String bookingId) {
    }
}
