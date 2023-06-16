package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Guest {
    private String userId;
    private String guestId;
    private String guestName;
    private String gender;
    private String guestCountry;
    private String guestZipcode;
    private String guestPassportId;
}
