package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormResponseDetails;
import dtapcs.springframework.Formee.domain.FormResponseDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormResponseDetailsRepository extends JpaRepository<FormResponseDetails, FormResponseDetailsId> {
}
