package dtapcs.springframework.Formee.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FormTemplateSummaryListDTO {
    List<FormTemplateSummaryDTO> formTemplateSummarys;
}