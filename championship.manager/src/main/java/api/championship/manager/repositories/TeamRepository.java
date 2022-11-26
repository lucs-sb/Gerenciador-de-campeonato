package api.championship.manager.repositories;

import api.championship.manager.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findByUserIdAndDeletionDateIsNull(Long user_id, Pageable pageable);

    @Query(value = "SELECT * FROM tb_team WHERE user_id = :user_id AND deletion_date IS NULL AND " +
            "(name like %:search% OR abbreviation like %:search%) /*#pageable*/",
            countQuery = "SELECT count(*) FROM tb_team WHERE user_id = :user_id AND deletion_date IS NULL AND " +
                    "(name like %:search% OR abbreviation like %:search%) /*#pageable*/", nativeQuery = true)
    Page<Team> findByNameOrAbbreviation(Long user_id, String search, Pageable pageable);

    @Query(value = "SELECT t.* FROM tb_team AS t " +
            "INNER JOIN tb_user AS u ON u.id = t.user_id " +
            "WHERE t.user_id = :user_id AND t.id = :id AND t.deletion_date IS NULL", nativeQuery = true)
    Optional<Team> findByIdAndUserId(Long id, Long user_id);
}