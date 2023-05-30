package lk.ijse.hotel.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BookingTM {
    private String guestId;
    private String bookingId;
    private String bookingDate;
    private String roomId;
    private String checkIn;
    private String checkOut;
}