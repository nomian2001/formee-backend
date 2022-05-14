package dtapcs.springframework.Formee.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class FormResponseDetailsId implements Serializable {
    @Column(name = "response_id")
    private UUID responseId;

    @Column(name = "product_id")
    private UUID productId;
}
