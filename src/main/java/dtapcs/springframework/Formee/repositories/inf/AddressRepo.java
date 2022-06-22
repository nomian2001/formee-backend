package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.AddressCommons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<AddressCommons, String> {
    List<AddressCommons> findByParentCode(String parentCode);
}
