package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forms")
@PreAuthorize("hasRole('USER')")
//@CrossOrigin(origins = "#{'${formee.url}'}")
public class FormController extends BaseController {
    @Autowired
    private FormService formService;

    @PostMapping("")
    public ResponseEntity<String> createForm(Form form) {
        return new ResponseEntity<>(
                formService.createForm(form), HttpStatus.OK);
    }
}
