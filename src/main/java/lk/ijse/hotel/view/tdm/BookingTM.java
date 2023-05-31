package lk.ijse.hotel.view.tdm;

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