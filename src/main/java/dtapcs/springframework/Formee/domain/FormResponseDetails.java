package dtapcs.springframework.Formee.domain;

import javax.persistence.*;


@Entity
public class FormResponseDetails{

    @EmbeddedId
    private FormResponseDetailsId id;

    private String responseId;
    private String productId;
    private int quantity;
    private Long productOrderPrice;

    public FormResponseDetails(String responseId, String productId, int quantity, Long productOrderPrice) {
        this.responseId = responseId;
        this.productId = productId;
        this.quantity = quantity;
        this.productOrderPrice = productOrderPrice;
    }

    public FormResponseDetails() {
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
                ", responseId='" + responseId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", productOrderPrice=" + productOrderPrice +
                '}';
    }
}
