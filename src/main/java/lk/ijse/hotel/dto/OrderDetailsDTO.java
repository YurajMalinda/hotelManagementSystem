package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsDTO {
    private String orderID;
    private String mealID;
    private Integer qty;
    private Double total;
    private String date;
}