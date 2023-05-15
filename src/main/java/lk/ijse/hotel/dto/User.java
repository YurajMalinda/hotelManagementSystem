package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class User {
    private String id;
    private String name;
    private String password;
    private String title;
}