package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SupplierTM implements Comparable<SupplierTM>{
    private String id;
    private String name;
    private String contact;
    private String details;

    @Override
    public int compareTo(SupplierTM o) {
        return id.compareTo(o.id);
    }
}
