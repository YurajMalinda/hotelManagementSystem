package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Tour {
    private String tourId;
    private String tourName;
    private String tourDetails;
    private Double price;
}
