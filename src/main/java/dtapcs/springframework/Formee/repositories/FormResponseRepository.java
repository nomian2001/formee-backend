package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FormResponseRepository extends CrudRepository<FormResponse, UUID> {
}
