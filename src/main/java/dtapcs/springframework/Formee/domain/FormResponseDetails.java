package dtapcs.springframework.Formee.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class FormResponseDetails{

    @EmbeddedId
    private FormResponseDetailsId id;

    private int quantity;
    private Long productOrderPrice;

    public FormResponseDetails(UUID responseId,UUID productId ,int quantity, Long productOrderPrice) {
        this.id = new FormResponseDetailsId(responseId,productId);
        this.quantity = quantity;
        this.productOrderPrice = productOrderPrice;
    }

    public FormResponseDetails() {
    }

    @Override
    public String toString() {
        return "FormResponseDetails{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productOrderPrice=" + productOrderPrice +
                '}';
    }
}
