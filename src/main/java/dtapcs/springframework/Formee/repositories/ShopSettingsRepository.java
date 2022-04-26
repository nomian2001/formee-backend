package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.ShopSettings;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShopSettingsRepository extends CrudRepository<ShopSettings, UUID> {
}
