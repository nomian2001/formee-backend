package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.FormMapper;
import dtapcs.springframework.Formee.dtos.mapper.FormOrderMapper;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forms")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class FormController extends BaseController {
    @Autowired
    private FormService formService;

    @PostMapping("/create")
    public ResponseEntity createForm(@RequestBody FormDTO item) {
        Form result = formService.createForm(item);
        FormDTO resultDTO = FormMapper.INSTANCE.FormToFormDTO(result);
        DataResponse response = DataResponse.ok()
                .withResult(resultDTO)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-permission")
    public ResponseEntity updatePermission(@RequestParam UUID formId) {
        formService.updatePermission(formId);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFormByID(@PathVariable UUID id) {
        DataResponse response = null;
        Form result = formService.getFormById(id);
        FormDTO resultDTO = FormMapper.INSTANCE.FormToFormDTO(result);
        if (result != null) {
            response = DataResponse.ok()
                    .withMessage(super.getMessage("message.common.success"))
                    .withResult(resultDTO)
                    .build();
        } else {
            response = DataResponse.badRequest()
                    .withMessage(super.getMessage("message.common.not.found"))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent/{userId}")
    public ResponseEntity getRecentForms(@PathVariable String userId) {
        List<Form> result = formService.getRecentForms(userId);
        List<FormDTO> resultDTO = result.stream().map(FormMapper.INSTANCE::FormToFormDTO).collect(Collectors.toList());
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(resultDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
