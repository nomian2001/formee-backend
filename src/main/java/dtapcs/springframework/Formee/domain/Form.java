package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class Form {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shopId;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "formId")
    Set<FormResponse> formResponses;
    private String layoutJson;

    public Form() {
    }

    public Form(Shop shopId, Set<FormResponse> formResponses, String layoutJson) {
        this.shopId = shopId;
        this.formResponses = formResponses;
        this.layoutJson = layoutJson;
    }

    public Set<FormResponse> getFormResponses() {
        return formResponses;
    }

    public void setFormResponses(Set<FormResponse> formResponses) {
        this.formResponses = formResponses;
    }

    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(Shop shopId) {
        this.shopId = shopId;
    }

    public String getLayoutJson() {
        return layoutJson;
    }

    public void setLayoutJson(String layoutJson) {
        this.layoutJson = layoutJson;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Form{" +
                "uuid=" + uuid +
                ", shopId=" + shopId +
                ", layoutJson='" + layoutJson + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Form form = (Form) o;

        return uuid.equals(form.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
