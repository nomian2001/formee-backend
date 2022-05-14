package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.FormResponseDetails;
import dtapcs.springframework.Formee.entities.FormResponseDetailsId;
import dtapcs.springframework.Formee.repositories.inf.FormResponseDetailsRepo;
import dtapcs.springframework.Formee.services.inf.FormResponseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseDetailServiceImpl implements FormResponseDetailService {
    @Autowired
    private FormResponseDetailsRepo formResponseDetailsRepo;

    @Override
    public FormResponseDetails getFormResponseDetail(FormResponseDetailsId id) {
        return formResponseDetailsRepo.getById(id);
    }

    @Override
    public String updateFormResponseDetail(FormResponseDetails details) {
        formResponseDetailsRepo.getById(details.getId()).UpdateFormDetails(details);
        return null;
    }
}
