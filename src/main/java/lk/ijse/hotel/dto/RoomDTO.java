package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RoomDTO {
    private String id;
    private String details;
    private String roomType;
    private Double price;
}
