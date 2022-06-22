package dtapcs.springframework.Formee.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "dm_dia_chi_commons")
public class AddressCommons {
    @Id
    private String code;

    private String name_;

    private String parentCode;

    private String type_;
}
