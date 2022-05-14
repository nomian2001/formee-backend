package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private FormRepo formRepo;

    @Override
    public String createForm(Form form) {
        formRepo.save(form);
        return null;
    }
}
