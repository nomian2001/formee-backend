package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.FormOrderMapper;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "#{'${formee.url}'}")
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormOrderController extends BaseController {

    @Autowired
    private FormOrderService formOrderService;
    @Autowired
    private FormService formService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestBody FormOrderDTO order) {
        FormOrder result = formOrderService.createOrder(order);
        FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
        DataResponse response = DataResponse.ok()
                .withResult(resultDTO)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/duplicate/{orderId}")
    public ResponseEntity duplicateOrder(@PathVariable UUID orderId) {
        FormOrder result = formOrderService.duplicateOrder(orderId);
        FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(resultDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{formId}")
    public ResponseEntity findAllByFormId(@PathVariable UUID formId) {
        List<FormOrder> result = formOrderService.filterOrder(formId, null, null, null);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/response/{id}")
    public ResponseEntity getFormTemplateByID(@PathVariable UUID id) {
        FormOrder result = formOrderService.getById(id);
        FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(resultDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity updateOrder(@RequestBody FormOrderDTO order, Principal principal) {
        UserDetails loginUser = (UserDetails) principal;
        if (formService.checkFormPermission(loginUser.getId(), order.getFormId())) {
            FormOrder result = formOrderService.updateOrder(order);
            FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
            if (result == null) {
                DataResponse response = DataResponse.badRequest()
                        .withMessage(super.getMessage("message.common.not.found"))
                        .build();
                return ResponseEntity.ok(response);
            } else {
                DataResponse response = DataResponse.ok()
                        .withMessage(super.getMessage("message.common.success"))
                        .withResult(resultDTO)
                        .build();
                return ResponseEntity.ok(response);
            }
        } else {
            DataResponse response = DataResponse.badRequest()
                    .withMessage(super.getMessage("message.common.unauthorized"))
                    .build();
            return ResponseEntity.ok(response);
        }
    }
}

