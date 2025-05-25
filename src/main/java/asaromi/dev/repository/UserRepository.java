package asaromi.dev.repository;

import asaromi.dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE '%' || LOWER(:username) || '%' OR LOWER(u.name) LIKE '%' || LOWER(:name) || '%' ")
    List<User> findUsersByNameOrUsername(String name, String username);
}
