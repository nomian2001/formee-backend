package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.Comment;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.helper.SendEmailRunnable;
import dtapcs.springframework.Formee.helper.SendEmailSSL;
import dtapcs.springframework.Formee.repositories.inf.CommentRepo;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    FormOrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Comment createComment(CommentDTO dto, Principal principal) {
        Optional<FormOrder> check = orderRepo.findById(dto.getOrderId());
        if (check.isPresent()) {
            FormOrder order = check.get();
            Comment comment = new Comment();
            comment.setMessage(dto.getMessage());
            comment.setOrderId(order);
            // send email
//            UserDetails userDetails = (UserDetails) principal;
//            String recipient = userRepo.getEmailByUserId(userDetails.getId());
            String title = "Yêu cầu chỉnh sửa đơn hàng";
            String content = String.join(" ",
                    principal.getName(),
                    "đã gửi yêu cầu chỉnh sửa đơn hàng vào lúc",
                    new Date().toString(),
                    "với nội dung: \n",
                    comment.getMessage());
            SendEmailRunnable runnable = new SendEmailRunnable(title, content, "ththanh5200@gmail.com");
            Thread thread = new Thread(runnable);
            thread.start();
            // save comment
            return commentRepo.save(comment);
        }
        return null;
    }
}
