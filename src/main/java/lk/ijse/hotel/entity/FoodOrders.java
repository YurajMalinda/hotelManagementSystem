package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class FoodOrders {
    private String orderId;
    private String date;
    private String bookingId;
}
