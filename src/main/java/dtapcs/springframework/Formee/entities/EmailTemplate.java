package dtapcs.springframework.Formee.entities;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class EmailTemplate {
    String title;
    String content;
}
