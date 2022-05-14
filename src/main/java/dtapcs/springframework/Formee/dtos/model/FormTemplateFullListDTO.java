package dtapcs.springframework.Formee.dtos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FormTemplateFullListDTO {
    List<FormTemplateFullDTO> formTemplateFulls;
}
