package lk.ijse.hotel.entity;

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
