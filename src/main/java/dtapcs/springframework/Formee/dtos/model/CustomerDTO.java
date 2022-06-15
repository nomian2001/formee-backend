package dtapcs.springframework.Formee.dtos.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDTO {
    UUID uuid;
    String name;
    String phone;
    String address;
    String userId;
}
