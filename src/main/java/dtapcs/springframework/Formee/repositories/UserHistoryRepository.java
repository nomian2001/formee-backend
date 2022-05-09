package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserHistoryRepository extends JpaRepository<UserHistory, UUID> {
}
