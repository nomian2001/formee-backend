package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.FormResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FormResponseListDTO {
    List<FormResponse> responses;
}
