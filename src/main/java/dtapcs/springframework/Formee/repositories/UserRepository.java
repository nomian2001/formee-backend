package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormeeUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<FormeeUser, String> {
}
