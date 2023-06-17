package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class FoodTM implements Comparable<FoodTM>{
    private String id;
    private String name;
    private String details;
    private Double price;

    @Override
    public int compareTo(FoodTM o) {
        return id.compareTo(o.id);
    }
}
