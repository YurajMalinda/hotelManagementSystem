package lk.ijse.hotel.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RoomTM {
    private String id;
    private String details;
    private String roomType;
    private Double price;
}
