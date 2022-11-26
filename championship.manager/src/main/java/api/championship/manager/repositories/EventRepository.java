package api.championship.manager.repositories;

import api.championship.manager.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByMatchIdAndDeletionDateIsNull(Long id, Pageable pageable);

    @Query(value = "SELECT e.* FROM tb_event AS e " +
            "INNER JOIN tb_player AS p ON e.player_id = p.id " +
            "WHERE match_id = :match_id AND e.deletion_date IS NULL AND " +
            "p.name like %:search%", nativeQuery = true)
    List<Event> findBySearchWithPlayers(Long match_id, String search);

    @Query(value = "SELECT * FROM tb_event " +
            "WHERE match_id = :match_id AND deletion_date IS NULL AND " +
            "description like %:search%", nativeQuery = true)
    List<Event> findBySearch(Long match_id, String search);
}
