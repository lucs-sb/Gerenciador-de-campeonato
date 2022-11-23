package api.championship.manager.repositories;

import api.championship.manager.models.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findByChampionshipIdAndDeletionDateIsNull(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM tb_match " +
            "WHERE championship_id = :championshipId AND " +
            "type = :type AND status = :status AND deletion_date IS NULL", nativeQuery = true)
    List<Match> findMatchesByTypeAndStatusAndChampionshipId(Long championshipId, int type, int status);

    @Query(value = "SELECT m.* FROM tb_match AS m " +
            "INNER JOIN tb_team AS t ON t.id = m.home_team_id " +
            "WHERE m.championship_id = :championshipId AND " +
            "(t.name like %:search% OR m.place like %:search% " +
            "OR m.scoreboard like %:search%) AND m.deletion_date IS NULL", nativeQuery = true)
    List<Match> findBySearch(Long championshipId, String search);

    @Query(value = "SELECT m.* FROM tb_match AS m " +
            "INNER JOIN tb_team AS t ON t.id = m.home_team_id " +
            "WHERE m.championship_id = :championshipId AND " +
            "(m.type = :search OR m.status = :search) AND m.deletion_date IS NULL", nativeQuery = true)
    List<Match> findByTypeAndStatusSearch(Long championshipId, int search);

    List<Match> findByChampionshipIdAndJourney(Long id, String journey);
}
