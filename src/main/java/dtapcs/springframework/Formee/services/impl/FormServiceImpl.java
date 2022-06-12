package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.mapper.FormTemplateSummaryMapper;
import dtapcs.springframework.Formee.dtos.model.FormDTO;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.enums.ResponsePermission;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.repositories.inf.FormTemplateRepo;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    public Form createForm(FormDTO formDTO) {
        Form form = new Form();
        form.UpdateForm(formDTO, new HashSet<>());
        return formRepo.save(form);
    }

    @Override
    public Form getFormById(UUID formId) {
        Optional<Form> result = formRepo.findById(formId);
        if (result.isPresent()) {
            Form item = result.get();
            item.setFormOrders(new HashSet<>());
            return item;
        }
        return null;
    }

    @Override
    public List<Form> getRecentForms(String userId) {
        List<Form> result = formRepo.findAllByUserId(userId);
//        result.forEach(form -> form.setFormOrders(null));
        return result;
    }

    @Override
    public Boolean checkFormPermission(String userId, UUID formId) {
        Optional<Form> check = formRepo.findById(formId);
        if (check.isPresent()) {
            Form form = check.get();
            if (form.getResponsePermission() == ResponsePermission.AllowAll) {
                return true;
            } else {
                return userId.equals(form.getUserId());
            }
        } else {
            return false;
        }
    }

    @Override
    public void updatePermission(UUID formId) {
        Optional<Form> result = formRepo.findById(formId);
        if (result.isPresent()) {
            Form item = result.get();
            item.setResponsePermission(item.getResponsePermission().equals(ResponsePermission.AllowAll)
                    ? ResponsePermission.OwnerOnly : ResponsePermission.AllowAll);
            formRepo.save(item);
        }
    }

}
