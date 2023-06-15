package lk.ijse.hotel.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderTM implements Comparable<OrderTM> {
    private String orderID;
    private String BookID;
    private String guestID;
    private String guestName;
    private String orderDate;
    private String FoodID;
    private String FoodName;
    private Double FoodPrice;
    private Integer qty;
    private Double total;
    private Button removeBtn;

    @Override
    public int compareTo(OrderTM o) {
        return orderID.compareTo(o.orderID);
    }
}