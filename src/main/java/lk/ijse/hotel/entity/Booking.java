package lk.ijse.hotel.entity;

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

    public Booking(String guestId, String roomId, String checkOut, String bookingId) {
    }
}
