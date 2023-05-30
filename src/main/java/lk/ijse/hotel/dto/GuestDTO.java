package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class GuestDTO {
    private String userId;
    private String id;
    private String name;
    private String gender;
    private String country;
    private String zipCode;
    private String passportId;
}
