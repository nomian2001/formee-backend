package dtapcs.springframework.Formee.api.model;

import dtapcs.springframework.Formee.domain.FormResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FormResponseListDTO {
    List<FormResponse> responses;
}
