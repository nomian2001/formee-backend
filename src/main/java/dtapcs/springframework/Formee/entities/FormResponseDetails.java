package dtapcs.springframework.Formee.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class FormResponseDetails {

    @EmbeddedId
    private FormResponseDetailsId id;

    private int quantity;

    private Long productOrderPrice;

    public void UpdateFormDetails(FormResponseDetails newDetails) {
        this.quantity = newDetails.getQuantity();
        this.productOrderPrice = newDetails.getProductOrderPrice();
    }

}
