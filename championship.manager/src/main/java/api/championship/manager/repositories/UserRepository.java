package api.championship.manager.repositories;

import api.championship.manager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM tb_user " +
            "WHERE email = :email AND deletion_date IS NULL", nativeQuery = true)
    Optional<User> findByEmail(String email);
}
