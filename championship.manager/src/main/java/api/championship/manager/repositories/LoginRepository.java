package api.championship.manager.repositories;

import api.championship.manager.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);

    @Query(value = "SELECT * FROM tb_login " +
            "WHERE user_id = :id", nativeQuery = true)
    Optional<Login> findByUser(Long id);
}
