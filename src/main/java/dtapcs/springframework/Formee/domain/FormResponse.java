package dtapcs.springframework.Formee.domain;

import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.enums.PaymentStatus;
import dtapcs.springframework.Formee.enums.SubmitStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;
@Entity
public class FormResponse {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;
    private Long formId;
    private Long customerId;
    private SubmitStatus submitStatus;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    int discountPercentage;

    public FormResponse() {
    }

    public FormResponse(Long formId, Long customerId, SubmitStatus submitStatus, OrderStatus orderStatus, PaymentStatus paymentStatus, int discountPercentage) {
        this.formId = formId;
        this.customerId = customerId;
        this.submitStatus = submitStatus;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.discountPercentage = discountPercentage;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public SubmitStatus getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(SubmitStatus submitStatus) {
        this.submitStatus = submitStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public UUID getUuid() {
        return uuid;
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
