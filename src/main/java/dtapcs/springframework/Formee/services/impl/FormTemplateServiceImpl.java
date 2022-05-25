package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.mapper.FormTemplateFullMapper;
import dtapcs.springframework.Formee.dtos.mapper.FormTemplateSummaryMapper;
import dtapcs.springframework.Formee.dtos.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.FormTemplate;
import dtapcs.springframework.Formee.repositories.inf.FormTemplateRepo;
import dtapcs.springframework.Formee.services.inf.FormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormTemplateServiceImpl implements FormTemplateService {
    @Autowired
    private FormTemplateSummaryMapper formTemplateSummaryMapper;
    @Autowired
    private FormTemplateFullMapper formTemplateFullMapper;
    @Autowired
    private FormTemplateRepo formTemplateRepo;

    @Override
    public List<FormTemplateSummaryDTO> getRecentTemplates(String username) {
        List<Object[]> responses = formTemplateRepo.getRecentResponses(username);
        List<String> recentForms = responses
                .stream()
                .map(response -> (String) response[0])
                .collect(Collectors.toList());
        return formTemplateRepo.findAll()
                .stream()
                .filter(formTemplate -> recentForms.contains(formTemplate.getUuid().toString()))
                .map(formTemplateSummaryMapper::formTemplateToFormTemplateSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FormTemplateSummaryDTO> getAllFormTemplateSummary() {
        return formTemplateRepo.findAll()
                .stream()
                .map(formTemplateSummaryMapper::formTemplateToFormTemplateSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FormTemplate getFormTemplateByID(UUID id) {
        return formTemplateRepo.getById(id);
    }

    @Override
    public List<FormTemplateFullDTO> getAllFormTemplateFull() {
        return formTemplateRepo.findAll()
                .stream()
                .map(formTemplateFullMapper::formTemplateToFormTemplateFullDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createFormTemplate(FormTemplate formTemplate) {
        formTemplateRepo.save(formTemplate);
    }
}
