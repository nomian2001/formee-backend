package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.entities.Comment;

import java.security.Principal;

public interface CommentService {
    Comment createComment(CommentDTO dto, Principal principal);
}
