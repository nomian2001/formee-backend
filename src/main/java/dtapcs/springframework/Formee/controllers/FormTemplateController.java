package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.FormTemplateFullListDTO;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryListDTO;
import dtapcs.springframework.Formee.entities.FormTemplate;
import dtapcs.springframework.Formee.services.inf.FormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/template")
public class FormTemplateController {
    @Autowired
    private FormTemplateService formTemplateService;

    @GetMapping("/gallery")
    public ResponseEntity<FormTemplateFullListDTO> getAllFormTemplateFull() {
        return new ResponseEntity<FormTemplateFullListDTO>(
                new FormTemplateFullListDTO(formTemplateService.getAllFormTemplateFull()), HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<FormTemplateSummaryListDTO> getAllFormTemplateSummary() {
        return new ResponseEntity<FormTemplateSummaryListDTO>(
                new FormTemplateSummaryListDTO(formTemplateService.getAllFormTemplateSummary()), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormTemplate> getFormTemplateByID(@PathVariable UUID id) {
        return new ResponseEntity<FormTemplate>(
                formTemplateService.getFormTemplateByID(id), HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFormTemplate(@RequestBody FormTemplate formTemplate) {
        return new ResponseEntity<>(
                formTemplateService.createFormTemplate(formTemplate), HttpStatus.OK
        );
    }

}
