package dtapcs.springframework.Formee.entities;

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
    private FormeeUser customerId; // for registed user

    private String customerName; // for anonymous customer

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

}
