package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.entities.Auditable;
import dtapcs.springframework.Formee.entities.Comment;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FormOrderMapper {
    FormOrderMapper INSTANCE = Mappers.getMapper(FormOrderMapper.class);

    @Named("formToFormId")
    static UUID formToFormId(Form form) {
        return form.getUuid();
    }

    @Named("commentsToCommentsDTO")
    static List<CommentDTO> commentsToCommentsDTO(Set<Comment> commentSet) {
        if (commentSet != null && commentSet.size() > 0) {
            List<Comment> commentList = new ArrayList<>(commentSet);
            return commentList.stream()
                    .map(CommentMapper.INSTANCE::commentToCommentDTO)
                    .sorted(Comparator.comparing(Auditable::getCreatedDate))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Named("orderStatusToEnum")
    static OrderStatus orderStatusToEnum(OrderStatus orderStatus) {
        return orderStatus;
    }

    @Mapping(source = "formId", target = "formId", qualifiedByName = "formToFormId")
    @Mapping(source = "comments", target = "comments", qualifiedByName = "commentsToCommentsDTO")
    @Mapping(source = "status", target = "status", qualifiedByName = "orderStatusToEnum")
    @Mapping(source = "confirmed", target = "confirmed")
    @Mapping(source = "requested", target = "requested")
    @Mapping(source = "discount", target = "discount")
    FormOrderDTO formOrderToFormOrderDTO(FormOrder formOrder);
}
