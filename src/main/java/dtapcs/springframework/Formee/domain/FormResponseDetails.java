package dtapcs.springframework.Formee.domain;

import javax.persistence.*;
import java.util.UUID;


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

    public UUID getResponseId() {
        return id.getResponseId();
    }

    public UUID getProductId() {
        return id.getProductId();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductOrderPrice() {
        return productOrderPrice;
    }

    public void setProductOrderPrice(Long productOrderPrice) {
        this.productOrderPrice = productOrderPrice;
    }

    public FormResponseDetailsId getId() {
        return id;
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
