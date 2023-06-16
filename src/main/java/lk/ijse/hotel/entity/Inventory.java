package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Inventory {
    private String itemId;
    private String itemName;
    private String itemDetails;
    private Double itemPrice;
}
