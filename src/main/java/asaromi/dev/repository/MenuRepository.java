package asaromi.dev.repository;

import asaromi.dev.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {

    @Query(value = "SELECT a.* FROM accesses a " +
            "WHERE (" +
            "    :search IS NULL OR (" +
            "        :search IS NOT NULL AND (" +
            "            a.name LIKE CONCAT('%', :search, '%') " +
            "            OR a.path LIKE CONCAT('%', :search, '%') " +
            "            OR a.description LIKE CONCAT('%', :search, '%')" +
            "        )" +
            "    )" +
            ") " +
            "AND (" +
            "    :isActive IS NULL OR (" +
            "        :isActive IS NOT NULL AND (" +
            "            a.is_active = :isActive" +
            "        )" +
            "    )" +
            ") " +
            "ORDER BY a.is_active " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Menu> findAllBySearchAndIsActive(String search, Integer isActive, Integer offset, Integer limit);
}
