package dtapcs.springframework.Formee.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class FormResponseDetailsId implements Serializable {
    @Column(name = "response_id")
    private UUID responseId;
    @Column(name = "product_id")
    private UUID productId;

    public FormResponseDetailsId() {
    }

    public FormResponseDetailsId(UUID responseId, UUID productId) {
        this.responseId = responseId;
        this.productId = productId;
    }

    public UUID getResponseId() {
        return responseId;
    }

    public void setResponseId(UUID responseId) {
        this.responseId = responseId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
