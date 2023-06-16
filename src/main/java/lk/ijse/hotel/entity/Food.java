package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Food {
    private String foodId;
    private String foodName;
    private String foodDetails;
    private Double foodPrice;
}
