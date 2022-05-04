package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.api.model.FormTemplateFullListDTO;
import dtapcs.springframework.Formee.domain.Form;
import dtapcs.springframework.Formee.services.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/forms/")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }
    @PostMapping
    public ResponseEntity<String> createForm (Form form){
        return new ResponseEntity<>(
                formService.createForm(form), HttpStatus.OK);
    }
}
