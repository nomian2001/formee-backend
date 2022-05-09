package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FormRepository extends JpaRepository<Form, UUID> {
}
