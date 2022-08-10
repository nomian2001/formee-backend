package dtapcs.springframework.Formee.controllers;

import com.itextpdf.text.DocumentException;
import dtapcs.springframework.Formee.dtos.mapper.FormOrderMapper;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.dtos.request.ExportRequest;
import dtapcs.springframework.Formee.dtos.request.FormOrderSearchRequest;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
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
        String result = formOrderService.duplicateOrder(orderId);
        DataResponse response;
        if (StringUtils.hasText(result)) {
            response = DataResponse.withCode(HttpStatus.INSUFFICIENT_STORAGE.value())
                    .withMessage(super.getMessage(result))
                    .build();
        } else {
            response = DataResponse.ok()
                    .withMessage(super.getMessage("message.common.success"))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/filter")
    public ResponseEntity filterOrder(@RequestBody FormOrderSearchRequest request) {
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        List<FormOrder> result = formOrderService.filterOrder(request.getOrderStatus(), request.getStartDate(),
                request.getEndDate(), request.getKeywords().toLowerCase(), request.getFormId());
        List<FormOrderDTO> dtos = result.stream()
                .map(FormOrderMapper.INSTANCE::formOrderToFormOrderDTO)
                .collect(Collectors.toList())
                .subList(pageNumber * pageSize, Math.min((pageNumber + 1) * pageSize, result.size()));
        Page<FormOrderDTO> page = new PageImpl<>(dtos, PageRequest.of(pageNumber, pageSize), result.size());
        DataResponse response = DataResponse.ok()
                .withResult(page)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/response/{id}")
    public ResponseEntity getResponseByID(@PathVariable UUID id) {
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
        UserDetails userDetails = CommonHelper.getCurrentUser();
        if (formService.checkFormPermission(userDetails.getUsername(), order.getFormId())) {
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

    @PostMapping("/export-invoice")
    public ResponseEntity<byte[]> exportInvoice(@RequestBody ExportRequest request) throws DocumentException, IOException, URISyntaxException {
        return formOrderService.exportInvoice(request);
    }
}

