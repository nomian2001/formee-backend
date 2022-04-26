package dtapcs.springframework.Formee.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FormResponseDetailsId implements Serializable {
    @Column(name = "response_id")
    private long responseId;
    @Column(name = "product_id")
    private long productId;
}
