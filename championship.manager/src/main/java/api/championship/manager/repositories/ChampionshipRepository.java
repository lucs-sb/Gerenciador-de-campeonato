package api.championship.manager.repositories;

import api.championship.manager.models.Championship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Page<Championship> findByUserIdAndDeletionDateIsNull(Long user_id, Pageable pageable);

    @Query(value = "SELECT * FROM tb_championship " +
            "WHERE user_id = :user_id AND deletion_date IS NULL AND " +
            "(status = :search OR number_of_teams = :search) " +
            "/*#pageable*/",
            countQuery = "SELECT count(*) FROM tb_championship " +
            "WHERE user_id = :user_id AND deletion_date IS NULL AND " +
            "(status = :search OR number_of_teams = :search) " +
            "/*#pageable*/", nativeQuery = true)
    Page<Championship> findByparams(Long user_id, int search, Pageable pageable);

    @Query(value = "SELECT * FROM tb_championship " +
            "WHERE user_id = :user_id AND deletion_date IS NULL AND " +
            "(name like %:search% OR award like %:search% OR description like %:search%) " +
            "/*#pageable*/",
            countQuery = "SELECT count(*) FROM tb_championship " +
            "WHERE user_id = :user_id AND deletion_date IS NULL AND " +
            "(name like %:search% OR award like %:search% OR description like %:search%) " +
            "/*#pageable*/", nativeQuery = true)
    Page<Championship> findByUserAndName(Long user_id, String search, Pageable pageable);
}
