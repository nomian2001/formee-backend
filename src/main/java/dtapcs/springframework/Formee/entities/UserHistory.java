package dtapcs.springframework.Formee.entities;

import dtapcs.springframework.Formee.enums.HistoryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class UserHistory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private FormeeUser userId;

    private HistoryType type;

    private LocalDateTime accessedAt;
}
