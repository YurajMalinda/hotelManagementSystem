package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class FoodOrderDetails {
    private String orderId;
    private String foodId;
    private Integer qty;
    private Double amount;
    private String date;
}
