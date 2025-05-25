package asaromi.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_accesses")
public class RoleMenu {

    @Id
    private String id;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UlidCreator.getUlid(System.currentTimeMillis()).toString().toLowerCase();
        }
    }

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private Boolean isActive;
}
