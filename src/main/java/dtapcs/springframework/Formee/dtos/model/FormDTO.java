package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.Auditable;
import dtapcs.springframework.Formee.enums.ResponsePermission;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class FormDTO extends Auditable {
    Set<UUID> formOrders;
    private UUID uuid;
    private String name;
    private String userId;
    private String templateId;
    private ResponsePermission responsePermission;
    private String layoutJson;
}
