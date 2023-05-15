package lk.ijse.hotel.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TourTM {
    private String id;
    private String name;
    private String details;
    private Double price;
}
