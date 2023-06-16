package lk.ijse.hotel.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class User {
    private String userId;
    private String userName;
    private String password;
    private String title;
}
