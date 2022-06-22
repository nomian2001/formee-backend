package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.AddressCommons;

import java.util.List;

public interface AddressService {
    List<AddressCommons> findAddressCommons(String parentCode);
}
