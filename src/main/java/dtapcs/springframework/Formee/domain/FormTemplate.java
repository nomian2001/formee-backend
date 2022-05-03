package dtapcs.springframework.Formee.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class FormTemplate {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;
    private String name;
    private String image;
    private String description;
    private String layoutJson;

    public FormTemplate() {
    }

    public FormTemplate(String name, String description, String layoutJson) {
        this.name = name;
        this.description = description;
        this.layoutJson = layoutJson;
    }

    @Override
    public String toString() {
        return "FormTemplate{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", layoutJson='" + layoutJson + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormTemplate that = (FormTemplate) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
