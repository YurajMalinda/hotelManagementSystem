package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Payment {
    private String paymentId;
    private String guestId;
    private String guestName;
    private String bookingId;
    private String roomId;
    private String checkInDate;
    private String checkOutDate;
    private Double ordersAmount;
    private Double totalPrice;
}
