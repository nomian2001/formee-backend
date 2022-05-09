package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.api.mapper.FormTemplateFullMapper;
import dtapcs.springframework.Formee.api.mapper.FormTemplateSummaryMapper;
import dtapcs.springframework.Formee.api.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.api.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.domain.FormTemplate;
import dtapcs.springframework.Formee.repositories.FormTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormTemplateServiceImpl implements FormTemplateService {
    private final FormTemplateSummaryMapper formTemplateSummaryMapper;
    private final FormTemplateFullMapper formTemplateFullMapper;
    private final FormTemplateRepository formTemplateRepository;

    public FormTemplateServiceImpl(FormTemplateSummaryMapper formTemplateSummaryMapper, FormTemplateFullMapper formTemplateFullMapper, FormTemplateRepository formTemplateRepository) {
        this.formTemplateSummaryMapper = formTemplateSummaryMapper;
        this.formTemplateFullMapper = formTemplateFullMapper;
        this.formTemplateRepository = formTemplateRepository;
    }

    @Override
    public List<FormTemplateSummaryDTO> getAllFormTemplateSummary() {
       return formTemplateRepository.findAll()
               .stream()
               .map(formTemplateSummaryMapper::formTemplateToFormTemplateSummaryDTO)
               .collect(Collectors.toList());
    }

    @Override
    public FormTemplate getFormTemplateByID(UUID id) {
        return formTemplateRepository.getById(id);
    }

    @Override
    public List<FormTemplateFullDTO> getAllFormTemplateFull() {
        return formTemplateRepository.findAll()
                .stream()
                .map(formTemplateFullMapper::formTemplateToFormTemplateFullDTO)
                .collect(Collectors.toList());
    }
    @Override
    public String createFormTemplate(FormTemplate formTemplate)
    {
        formTemplateRepository.save(formTemplate);
        return null;
    }
}
