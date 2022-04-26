package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.UserSettings;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserSettingsRepository extends CrudRepository<UserSettings, UUID> {
}
