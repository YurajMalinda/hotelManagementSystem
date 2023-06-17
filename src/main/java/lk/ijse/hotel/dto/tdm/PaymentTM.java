package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentTM implements Comparable<PaymentTM>{
    String paymentId;
    String guestId;
    String guestName;
    String resId;
    String roomId;
    String checkIn;
    String checkOut;
    Double orderAm;
    Double total;

    @Override
    public int compareTo(PaymentTM o) {
        return paymentId.compareTo(o.paymentId);
    }
}
