package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.Comment;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.helper.SendEmailRunnable;
import dtapcs.springframework.Formee.helper.SendEmailSSL;
import dtapcs.springframework.Formee.repositories.inf.CommentRepo;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Comment createComment(CommentDTO dto, Boolean isRequest, Boolean isFromEdit) {
        Optional<FormOrder> check = orderRepo.findById(dto.getOrderId());
        if (check.isPresent()) {
            FormOrder order = check.get();
            Comment comment = new Comment();
            comment.setMessage(dto.getMessage());
            comment.setOrderId(order);

            if (isRequest) {
                // send email
                UserDetails userDetails = CommonHelper.getCurrentUser();
                String recipient = userRepo.getEmailByUsername(order.getCreatedBy());
                String title = "";
                String content = "";

                if (isFromEdit) {
                    title = "????n h??ng " + order.getOrderName() + " ???? ???????c ch???nh s???a";
                    content = String.join(" ",
                            userDetails.getUsername(),
                            "???? ch???nh s???a ????n h??ng",
                            order.getOrderName(),
                            "v??o l??c",
                            new Date().toGMTString(),
                            "v???i n???i dung:",
                            "\n\n" + comment.getMessage());
                }
                else {
                    title = "Y??u c???u ch???nh s???a ????n h??ng " + order.getOrderName();
                    content = String.join(" ",
                            userDetails.getUsername(),
                            "???? g???i y??u c???u ch???nh s???a ????n h??ng",
                            order.getOrderName(),
                            "v??o l??c",
                            new Date().toGMTString(),
                            "v???i n???i dung:",
                            "\n\n" + comment.getMessage());
                }

                SendEmailRunnable runnable = new SendEmailRunnable(title, content, recipient);
                Thread thread = new Thread(runnable);
                thread.start();
                // save items
                order.setStatus(OrderStatus.REQUESTED);
                orderRepo.save(order);
            }

            UserDetails userDetails = CommonHelper.getCurrentUser();
            if (userDetails.getUsername().equals("anonymousUser")) {
                comment.setCreatedBy(dto.getCustomerName());
            }

            return commentRepo.save(comment);
        }
        return null;
    }
}
