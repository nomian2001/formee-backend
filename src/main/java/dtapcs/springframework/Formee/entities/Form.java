package dtapcs.springframework.Formee.entities;

import dtapcs.springframework.Formee.dtos.model.FormDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.enums.ResponsePermission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Form extends Auditable {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "formId")
    Set<FormOrder> formOrders;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private String name;

    private String userId;

    private String templateId;

    private String color;

    private String imagePath;

    private ResponsePermission responsePermission;

    @Column(name = "layoutJson", columnDefinition = "TEXT")
    private String layoutJson;

    private Long untitledCount = 1L;

    public void InitFormList() {
        formOrders = new HashSet<>();
    }

    public void UpdateForm(FormDTO dto, Set<FormOrder> orders) {
        formOrders = orders;
        UpdateForm(dto);
    }

    public void UpdateForm(FormDTO dto) {
        createdBy = dto.getCreatedBy();
        createdDate = dto.getCreatedDate();
        lastModifiedBy = dto.getLastModifiedBy();
        lastModifiedDate = dto.getLastModifiedDate();
        name = dto.getName();
        userId = dto.getUserId();
        templateId = dto.getTemplateId();
        layoutJson = dto.getLayoutJson();
        responsePermission = dto.getResponsePermission();
        color = dto.getColor();
        imagePath = dto.getImagePath();
    }

    public void AddOrder(FormOrder order) {
        formOrders.add(order);
    }
}
