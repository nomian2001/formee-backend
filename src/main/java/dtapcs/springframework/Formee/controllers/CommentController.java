package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.CommentMapper;
import dtapcs.springframework.Formee.dtos.mapper.FormOrderMapper;
import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.entities.Comment;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.services.inf.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/order/comments")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class CommentController extends BaseController {
    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentDTO comment) {
        Comment result = commentService.createComment(comment, true);
        CommentDTO resultDTO = CommentMapper.INSTANCE.commentToCommentDTO(result);
        DataResponse response = DataResponse.ok()
                .withResult(resultDTO)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }
}
