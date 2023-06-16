package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Room {
    private String roomId;
    private String roomDetails;
    private String roomType;
    private Double roomPrice;
}
