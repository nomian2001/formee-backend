package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.*;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.entities.FormTemplate;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
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
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormOrderController extends BaseController {

    @Autowired
    private FormOrderService formOrderService;

    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestBody FormOrder order) {
        FormOrder result = formOrderService.createOrder(order);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userName}")
    public ResponseEntity findAllByUsername(@PathVariable String userName) {
        List<FormOrder> result = formOrderService.findAllByUserName(userName);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/response/{id}")
    public ResponseEntity getFormTemplateByID(@PathVariable UUID id) {
        FormOrder result = formOrderService.getById(id);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }
}

