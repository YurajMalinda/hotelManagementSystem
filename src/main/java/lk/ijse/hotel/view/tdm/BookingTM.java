package lk.ijse.hotel.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BookingTM implements Comparable<BookingTM> {
    private String guestId;
    private String bookingId;
    private String bookingDate;
    private String roomId;
    private String checkIn;
    private String checkOut;

    @Override
    public int compareTo(BookingTM o) {
        return bookingId.compareTo(o.getBookingId());
    }
}