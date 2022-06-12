package dtapcs.springframework.Formee.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormOrder extends Auditable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private String orderName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formId")
    private Form formId;

    private Boolean confirmed;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderId")
    Set<Comment> comments;

    public void UpdateFormOrder(FormOrderDTO dto) {
        orderName = dto.getOrderName();
        response = dto.getResponse();
        createdBy = dto.getCreatedBy();
        createdDate = dto.getCreatedDate();
        lastModifiedBy = dto.getLastModifiedBy();
        lastModifiedDate = dto.getLastModifiedDate();
        confirmed = dto.getConfirmed();
    }

    public void UpdateFormOrder(FormOrderDTO dto, Form form) {
        formId = form;
        UpdateFormOrder(dto);
    }
}
