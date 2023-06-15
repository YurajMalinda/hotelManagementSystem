package lk.ijse.hotel.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TourTM implements Comparable<TourTM> {
    private String id;
    private String name;
    private String details;
    private Double price;

    @Override
    public int compareTo(TourTM o) {
        return id.compareTo(o.id);
    }
}
