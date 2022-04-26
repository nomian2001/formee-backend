package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private String orderId;
    private Boolean fromSeller;
    private String parentCommentId;

    public Comment() {
    }

    public Comment(String orderId, Boolean fromSeller, String parentCommentId) {
        this.orderId = orderId;
        this.fromSeller = fromSeller;
        this.parentCommentId = parentCommentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getFromSeller() {
        return fromSeller;
    }

    public void setFromSeller(Boolean fromSeller) {
        this.fromSeller = fromSeller;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "uuid=" + uuid +
                ", orderId='" + orderId + '\'' +
                ", fromSeller=" + fromSeller +
                ", parentCommentId='" + parentCommentId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return uuid.equals(comment.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
