package asaromi.dev.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accesses")
public class Menu {
    @Id
    private String id;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UlidCreator.getUlid(System.currentTimeMillis()).toString().toLowerCase();
        }
    }

    @NotNull
    private String name;

    @NotNull
    private String path;

    @NotNull
    private String description;

    @Column(name = "parent_id")
    @JsonProperty("parent_id")
    private String parentId;

    @NotNull
    @Column(name = "is_active")
    @JsonProperty("is_active")
    private Boolean isActive;

    @Column(name = "updated_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Jakarta")
    private Date updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deletedAt;
}
