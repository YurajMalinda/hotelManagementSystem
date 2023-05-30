package lk.ijse.hotel.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class InventoryDTO {
    private String id;
    private String name;
    private String details;
    private Double price;
}
