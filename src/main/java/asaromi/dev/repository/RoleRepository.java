package asaromi.dev.repository;

import asaromi.dev.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "SELECT * FROM roles r " +
            "WHERE " +
            "(" +
            "   :isSearching = 0" +
            "   OR LOWER(r.name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "   OR LOWER(r.description) LIKE LOWER(CONCAT('%', :search, '%'))" +
            ")" +
            "AND (" +
                ":isActive IS NULL OR :isActive NOT IN (0, 1) " +
                "OR (:isActive = 1 and r.deleted_at IS NULL)" +
                "OR (:isActive = 0 and r.deleted_at IS NOT NULL)" +
            ")" +
            "ORDER BY r.id DESC " +
            "LIMIT :limit, :offset",
            nativeQuery = true
    )
    List<Role> findAllBySearchAndIsActive(Integer offset, Integer limit, Integer isSearching, String search, Integer isActive);
}
