package api.championship.manager.repositories;

import api.championship.manager.models.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findByChampionshipId(Long id, Pageable pageable);
}
