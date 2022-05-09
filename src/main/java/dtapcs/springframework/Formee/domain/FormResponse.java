package dtapcs.springframework.Formee.domain;

import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.enums.PaymentStatus;
import dtapcs.springframework.Formee.enums.SubmitStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class FormResponse {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;
//    @OneToMany (fetch = FetchType.LAZY, mappedBy = "response_id")
//    Set<FormResponseDetails> formResponseDetails;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "orderId")
    Set<Comment> comments;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="customerId")
    private FormeeUser customerId; //for registed user
    private String customerName; //for anonymous customer
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="formId")
    private Form formId;
    @OneToOne (fetch = FetchType.LAZY, mappedBy = "orderId")
    OrderTracking orderTracking;
    private SubmitStatus submitStatus;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdDate;
    int discountPercentage;

    public FormResponse() {
    }

    public FormResponse(Set<Comment> comments, FormeeUser customerId, Form formId, OrderTracking orderTracking, SubmitStatus submitStatus, OrderStatus orderStatus, PaymentStatus paymentStatus, int discountPercentage) {
        this.comments = comments;
        this.customerId = customerId;
        this.formId = formId;
        this.orderTracking = orderTracking;
        this.submitStatus = submitStatus;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "FormResponse{" +
                "uuid=" + uuid +
                ", formId=" + formId +
                ", customerId=" + customerId +
                ", submitStatus=" + submitStatus +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    public String getCustomerName() {
        if(customerId!=null)
            return customerId.getFullName();
        else
            return customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormResponse that = (FormResponse) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
