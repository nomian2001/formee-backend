package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.*;
import dtapcs.springframework.Formee.entities.FormTemplate;
import dtapcs.springframework.Formee.services.inf.FormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
@RequestMapping(value = "/api/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormTemplateController extends BaseController {

    @Autowired
    private FormTemplateService formTemplateService;

    @GetMapping("/gallery")
    public ResponseEntity getAllFormTemplateFull() {
        List<FormTemplateFullDTO> result = formTemplateService.getAllFormTemplateFull();
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/home")
    public ResponseEntity getAllFormTemplateSummary() {
        List<FormTemplateSummaryDTO> result = formTemplateService.getAllFormTemplateSummary();
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent/{username}")
    public ResponseEntity getRecentTemplates(@PathVariable String username) {
        List<FormTemplateSummaryDTO> result = formTemplateService.getRecentTemplates(username);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFormTemplateByID(@PathVariable UUID id) {
        FormTemplate result = formTemplateService.getFormTemplateByID(id);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity createFormTemplate(@RequestBody FormTemplate formTemplate) {
        formTemplateService.createFormTemplate(formTemplate);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

}
