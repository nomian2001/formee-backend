package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, UUID> {
}
