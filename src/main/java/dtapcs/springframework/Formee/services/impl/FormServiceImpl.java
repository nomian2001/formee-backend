package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.mapper.FormTemplateSummaryMapper;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.Form;
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
    public List<FormTemplateSummaryDTO> getRecentForms(String userId) {
        String username = userRepo.getUsernameById(userId);
        List<Object[]> responses = formRepo.getRecentResponses(username);
        List<String> recentFormIds = responses
                .stream()
                .map(response -> (String) response[0])
                .collect(Collectors.toList());
        return null;
    }
}
