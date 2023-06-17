package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class InventoryTM implements Comparable<InventoryTM>{
    private String id;
    private String name;
    private String details;
    private Double price;

    @Override
    public int compareTo(InventoryTM o) {
        return id.compareTo(o.id);
    }
}
