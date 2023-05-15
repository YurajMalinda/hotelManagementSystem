package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    String paymentId;
    String guestId;
    String guestName;
    String resId;
    String roomId;
    String checkIn;
    String checkOut;
    Double orderAm;
    Double total;

}
