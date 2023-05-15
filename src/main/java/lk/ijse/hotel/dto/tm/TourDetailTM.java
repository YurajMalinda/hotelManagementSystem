package lk.ijse.hotel.dto.tm;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TourDetailTM {
    private String bookingId;
    private String tourId;
    private String amount;
    private String date;
}
