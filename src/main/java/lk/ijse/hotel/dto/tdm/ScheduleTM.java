package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ScheduleTM implements Comparable<ScheduleTM>{
    private String id;
    private String details;

    @Override
    public int compareTo(ScheduleTM o) {
        return id.compareTo(o.id);
    }
}
