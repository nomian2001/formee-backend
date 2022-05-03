package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FormTemplateRepository extends JpaRepository<FormTemplate, UUID> {
}
