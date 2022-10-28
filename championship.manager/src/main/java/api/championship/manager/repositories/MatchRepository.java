package api.championship.manager.repositories;

import api.championship.manager.models.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findByChampionshipId(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM tb_match " +
            "WHERE championship_id = :championshipId AND type = :type AND status = :status", nativeQuery = true)
    List<Match> findMatchesByTypeAndStatusAndChampionshipId(Long championshipId, int type, int status);
}
