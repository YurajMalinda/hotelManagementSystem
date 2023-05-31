package lk.ijse.hotel.tm;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TourDetail {
    private String bookingId;
    private String tourId;
    private String amount;
    private String date;
}