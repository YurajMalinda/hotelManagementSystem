package lk.ijse.hotel.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTM {
    private String userId;
    private String id;
    private String name;
    private String gender;
    private String email;
    private String nic;
    private String address;
}
