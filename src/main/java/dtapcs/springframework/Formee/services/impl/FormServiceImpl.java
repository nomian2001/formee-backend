package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.mapper.FormTemplateSummaryMapper;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.enums.ResponsePermission;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.repositories.inf.FormTemplateRepo;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FormRepo formRepo;
    @Autowired
    private FormTemplateRepo formTemplateRepo;
    @Autowired
    private FormTemplateSummaryMapper formTemplateSummaryMapper;

    @Override
    public Form createForm(Form form) {
        return formRepo.save(form);
    }

    @Override
    public Optional<Form> getFormById(UUID formId) {
        return formRepo.findById(formId);
    }

    @Override
    public List<Form> getRecentForms(String userId) {
        return formRepo.getRecentForms(userId);
    }

    @Override
    public Boolean checkFormPermission(String userId, UUID formID){
        Form check = formRepo.getById(formID);
        if(check!=null){
            if(check.getResponsePermission() == ResponsePermission.AllowAll){
                return true;
            }
            else{
                return userId==check.getUserId();
            }
        }
        else {
            return false;
        }
    }

}
