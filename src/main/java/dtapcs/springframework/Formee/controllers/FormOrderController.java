package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.FormOrderMapper;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.dtos.request.FormOrderSearchRequest;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @PostMapping("/filter")
    public ResponseEntity filterOrder(@RequestBody FormOrderSearchRequest request) {
        List<FormOrder> result = formOrderService.filterOrder(request.getOrderStatus(), request.getStartDate(),
                                                request.getEndDate(), request.getKeywords(), request.getFormId());
        List<FormOrderDTO> dtos = result.stream()
                .map(FormOrderMapper.INSTANCE::formOrderToFormOrderDTO)
                .collect(Collectors.toList());
        DataResponse response = DataResponse.ok()
                .withResult(dtos)
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

    @GetMapping("/recent")
    public ResponseEntity getRecentOrders() {
        List<FormOrder> result = formOrderService.findRecentOrders();
        List<FormOrderDTO> dtos = result.stream()
                .map(FormOrderMapper.INSTANCE::formOrderToFormOrderDTO)
                .collect(Collectors.toList());
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(dtos)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity updateOrder(@RequestBody FormOrderDTO order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        if (formService.checkFormPermission(userDetails.getId(), order.getFormId())) {
            FormOrder result = formOrderService.updateOrder(order, false);
            FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
            DataResponse response;
            if (result == null) {
                response = DataResponse.badRequest()
                        .withMessage(super.getMessage("message.common.not.found"))
                        .build();
            } else {
                response = DataResponse.ok()
                        .withMessage(super.getMessage("message.common.success"))
                        .withResult(resultDTO)
                        .build();
            }
            return ResponseEntity.ok(response);
        } else {
            DataResponse response = DataResponse.badRequest()
                    .withMessage(super.getMessage("message.common.unauthorized"))
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity updateOrderStatus(@RequestBody FormOrderDTO order) {
        FormOrder result = formOrderService.updateOrder(order, true);
        FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
        DataResponse response;
        if (result == null) {
            response = DataResponse.badRequest()
                    .withMessage(super.getMessage("message.common.not.found"))
                    .build();
        } else {
            response = DataResponse.ok()
                    .withMessage(super.getMessage("message.common.success"))
                    .withResult(resultDTO)
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/export")
    public ResponseEntity<Resource> exportExcel(@RequestBody FormOrderSearchRequest request) {
        return formOrderService.export(request);
    }
}

