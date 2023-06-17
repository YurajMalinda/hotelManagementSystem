package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class LoginDTO {
    private String userName;
    private String passWord;
    private String title;
}
