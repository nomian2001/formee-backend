package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.entities.Comment;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Named("orderToOrderId")
    static UUID orderToOrderId(FormOrder order) {
        return order.getUuid();
    }

    @Mapping(source = "orderId", target = "orderId", qualifiedByName = "orderToOrderId")
    CommentDTO commentToCommentDTO(Comment comment);
}

