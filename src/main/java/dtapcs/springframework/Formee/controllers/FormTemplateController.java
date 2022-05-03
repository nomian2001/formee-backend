package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.api.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.api.model.FormTemplateFullListDTO;
import dtapcs.springframework.Formee.services.FormTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

}
