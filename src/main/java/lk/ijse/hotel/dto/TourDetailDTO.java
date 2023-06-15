package lk.ijse.hotel.dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TourDetailDTO {
    private String bookingId;
    private String tourId;
    private String amount;
    private String date;
}

