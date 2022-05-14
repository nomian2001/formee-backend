package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.FormResponseDetails;
import dtapcs.springframework.Formee.entities.FormResponseDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormResponseDetailsRepo extends JpaRepository<FormResponseDetails, FormResponseDetailsId> {
}
