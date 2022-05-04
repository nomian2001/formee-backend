package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.api.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.api.model.FormTemplateFullListDTO;
import dtapcs.springframework.Formee.api.model.FormTemplateSummaryListDTO;
import dtapcs.springframework.Formee.domain.FormTemplate;
import dtapcs.springframework.Formee.services.FormTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/template/")
public class FormTemplateController {
    private final FormTemplateService formTemplateService;

    public FormTemplateController(FormTemplateService formTemplateService) {
        this.formTemplateService = formTemplateService;
    }
    @GetMapping ("gallery")
    public ResponseEntity<FormTemplateFullListDTO> getAllFormTemplateFull (){
        return new ResponseEntity<FormTemplateFullListDTO>(
                new FormTemplateFullListDTO(formTemplateService.getAllFormTemplateFull()),HttpStatus.OK);
    }
    @GetMapping("home")
    public ResponseEntity<FormTemplateSummaryListDTO> getAllFormTemplateSummary(){
        return new ResponseEntity<FormTemplateSummaryListDTO>(
                new FormTemplateSummaryListDTO(formTemplateService.getAllFormTemplateSummary()),HttpStatus.OK
        );
    }
    @GetMapping("{id}")
    public ResponseEntity<FormTemplate> getFormTemplateByID(@PathVariable UUID id){
        return new ResponseEntity<FormTemplate>(
                formTemplateService.getFormTemplateByID(id),HttpStatus.OK
        );

    }
    @PostMapping("create")
    public ResponseEntity<String> createFormTemplate(FormTemplate formTemplate){
        return new ResponseEntity<>(
                formTemplateService.createFormTemplate(formTemplate),HttpStatus.OK
        );
    }

}
