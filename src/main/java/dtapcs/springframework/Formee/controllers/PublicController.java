package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.CommentMapper;
import dtapcs.springframework.Formee.dtos.mapper.FormMapper;
import dtapcs.springframework.Formee.dtos.mapper.FormOrderMapper;
import dtapcs.springframework.Formee.dtos.model.*;
import dtapcs.springframework.Formee.entities.AddressCommons;
import dtapcs.springframework.Formee.entities.Comment;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.services.inf.AddressService;
import dtapcs.springframework.Formee.services.inf.CommentService;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/public", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicController extends BaseController {
    @Autowired
    private FormService formService;

    @Autowired
    private FormOrderService formOrderService;

    @Autowired
    CommentService commentService;

    @Autowired
    AddressService addressService;

    @GetMapping("/forms/{id}")
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

    @GetMapping("/order/response/{id}")
    public ResponseEntity getResponseByID(@PathVariable UUID id) {
        FormOrder result = formOrderService.getById(id);
        FormOrderDTO resultDTO = FormOrderMapper.INSTANCE.formOrderToFormOrderDTO(result);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(resultDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/order/update-status")
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

    @PutMapping("/order/update")
    public ResponseEntity updateOrder(@RequestBody FormOrderDTO order) {
        UserDetails userDetails = CommonHelper.getCurrentUser();
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

    @PostMapping("/order/comments/create")
    public ResponseEntity createComment(@RequestBody CommentDTO comment) {
        Comment result = commentService.createComment(comment, true, comment.getFromEdit());
        CommentDTO resultDTO = CommentMapper.INSTANCE.commentToCommentDTO(result);
        DataResponse response = DataResponse.ok()
                .withResult(resultDTO)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/address")
    public ResponseEntity findAddressCommons(@RequestParam(name = "parentCode", defaultValue = "") String parentCode) {
        List<AddressCommons> result = addressService.findAddressCommons(parentCode);
        result.sort(Comparator.comparing(AddressCommons::getName_));
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }
}
