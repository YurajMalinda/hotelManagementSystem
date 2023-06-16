package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
    private String paymentId;
    private String guestId;
    private String guestName;
    private String resId;
    private String roomId;
    private String checkIn;
    private String checkOut;
    private Double orderAm;
    private Double total;
}
