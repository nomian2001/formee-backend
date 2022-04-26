package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormResponseDetails;
import dtapcs.springframework.Formee.domain.FormResponseDetailsId;
import org.springframework.data.repository.CrudRepository;

public interface FormResponseDetailsRepository extends CrudRepository<FormResponseDetails, FormResponseDetailsId> {
}
