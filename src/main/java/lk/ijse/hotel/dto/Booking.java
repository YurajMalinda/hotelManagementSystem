package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Booking {
    private String guestId;
    private String bookingId;
    private String bookingDate;
    private String roomId;
    private String checkIn;
    private String checkOut;
}
