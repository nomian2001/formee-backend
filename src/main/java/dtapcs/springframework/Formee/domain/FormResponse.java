package dtapcs.springframework.Formee.domain;

import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.enums.PaymentStatus;
import dtapcs.springframework.Formee.enums.SubmitStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
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
//    @OneToMany (fetch = FetchType.LAZY, mappedBy = id.getResponseId())
//    Set<FormResponseDetails> formResponseDetails;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "orderId")
    Set<Comment> comments;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="customerId")
    private FormeeUser customerId;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="formId")
    private Form formId;
    @OneToOne (fetch = FetchType.LAZY, mappedBy = "orderId")
    OrderTracking orderTracking;
    private SubmitStatus submitStatus;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public OrderTracking getOrderTracking() {
        return orderTracking;
    }

    public void setOrderTracking(OrderTracking orderTracking) {
        this.orderTracking = orderTracking;
    }

    public Form getFormId() {
        return formId;
    }

    public void setFormId(Form formId) {
        this.formId = formId;
    }

    public FormeeUser getCustomerId() {
        return customerId;
    }

    public void setCustomerId(FormeeUser customerId) {
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
