package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Employee {
    private String userId;
    private String id;
    private String name;
    private String gender;
    private String email;
    private String nic;
    private String address;
}
