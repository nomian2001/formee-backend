package dtapcs.springframework.Formee.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="formId")
    private Form formId;


    @Column(name = "response", columnDefinition = "TEXT")
    private String response;
    public void UpdateFormOrder(FormOrderDTO dto)
    {
        orderName = dto.getOrderName();
        response = dto.getResponse();
        createdBy = dto.getCreatedBy();
        createdDate = dto.getCreatedDate();
        lastModifiedBy = dto.getLastModifiedBy();
        lastModifiedDate = dto.getLastModifiedDate();
    }
    public void UpdateFormOrder(FormOrderDTO dto, Form form )
    {
        formId = form;
        UpdateFormOrder(dto);
    }
}
