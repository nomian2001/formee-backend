package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.Form;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FormRepository extends CrudRepository<Form, UUID> {
}
