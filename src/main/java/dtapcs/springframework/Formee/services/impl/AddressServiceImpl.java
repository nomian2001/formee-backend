package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.AddressCommons;
import dtapcs.springframework.Formee.repositories.inf.AddressRepo;
import dtapcs.springframework.Formee.services.inf.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepo addressRepo;

    @Override
    public List<AddressCommons> findAddressCommons(String parentCode) {
        return addressRepo.findByParentCode(StringUtils.hasText(parentCode) ? parentCode : null);
    }
}
