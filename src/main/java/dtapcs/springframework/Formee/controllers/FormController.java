package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormTemplate;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/forms")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class FormController extends BaseController {
    @Autowired
    private FormService formService;

    @PostMapping("/create")
    public ResponseEntity createForm(@RequestBody Form item) {
        Form result = formService.createForm(item);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFormByID(@PathVariable UUID id) {
        DataResponse response = null;
        Optional<Form> result = formService.getFormById(id);
        if (result.isPresent()) {
            response = DataResponse.ok()
                    .withMessage(super.getMessage("message.common.success"))
                    .withResult(result)
                    .build();
        }
        else {
            response = DataResponse.badRequest()
                    .withMessage(super.getMessage("message.common.not.found"))
                    .withResult(result)
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent/{userId}")
    public ResponseEntity getRecentForms(@PathVariable String userId) {
        List<FormTemplateSummaryDTO> result = formService.getRecentForms(userId);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }
}
