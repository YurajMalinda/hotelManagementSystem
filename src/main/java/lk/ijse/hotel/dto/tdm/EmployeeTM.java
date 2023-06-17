package lk.ijse.hotel.dto.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTM implements Comparable<EmployeeTM> {
    private String userId;
    private String id;
    private String name;
    private String gender;
    private String email;
    private String nic;
    private String address;

    @Override
    public int compareTo(EmployeeTM o) {
        return id.compareTo(o.id);
    }
}
