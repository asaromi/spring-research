package asaromi.dev.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class User {

    @Id
    private String id;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UlidCreator.getUlid(System.currentTimeMillis()).toString().toLowerCase();
        }
    }

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @Column(name = "active_token")
    private String activeToken;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;
}
