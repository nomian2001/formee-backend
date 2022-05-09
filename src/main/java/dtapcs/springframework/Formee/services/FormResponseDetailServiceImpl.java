package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.FormResponseDetails;
import dtapcs.springframework.Formee.domain.FormResponseDetailsId;
import dtapcs.springframework.Formee.repositories.FormResponseDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class FormResponseDetailServiceImpl implements FormResponseDetailService {
    private final FormResponseDetailsRepository formResponseDetailsRepository;

    public FormResponseDetailServiceImpl(FormResponseDetailsRepository formResponseDetailsRepository) {
        this.formResponseDetailsRepository = formResponseDetailsRepository;
    }

    @Override
    public FormResponseDetails getFormResponseDetail(FormResponseDetailsId id) {
        return formResponseDetailsRepository.getById(id);
    }

    @Override
    public String updateFormResponseDetail(FormResponseDetails details) {
        formResponseDetailsRepository.getById(details.getId()).UpdateFormDetails(details);
        return null;
    }
}
