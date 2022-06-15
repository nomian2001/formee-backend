package dtapcs.springframework.Formee.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Customer extends Auditable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private String name;

    private String phone;

    private String address;

    private String userId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userId")
//    private FormeeUser userId;
}
