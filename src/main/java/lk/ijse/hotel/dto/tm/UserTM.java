package lk.ijse.hotel.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class UserTM {
    private String id;
    private String name;
    private String password;
    private String title;
}
