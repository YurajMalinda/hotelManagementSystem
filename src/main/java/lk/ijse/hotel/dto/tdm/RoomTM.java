package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RoomTM implements Comparable<RoomTM> {
    private String id;
    private String details;
    private String roomType;
    private Double price;

    @Override
    public int compareTo(RoomTM o) {
        return id.compareTo(o.id);
    }
}
