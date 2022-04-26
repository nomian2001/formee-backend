package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
