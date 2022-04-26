package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FormTemplateRepository extends CrudRepository<FormTemplate, UUID> {
}
