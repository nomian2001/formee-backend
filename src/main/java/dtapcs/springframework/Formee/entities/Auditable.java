package dtapcs.springframework.Formee.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @Column(updatable = false)
    @CreatedBy
    protected String createdBy;

    @Column(updatable = false)
    @CreatedDate
    //@Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    //@Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime lastModifiedDate;
}
