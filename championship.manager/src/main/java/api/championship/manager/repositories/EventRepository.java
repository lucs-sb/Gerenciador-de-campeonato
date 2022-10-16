package api.championship.manager.repositories;

import api.championship.manager.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM tb_event " +
            "WHERE match_id = :id", nativeQuery = true)
    List<Event> findByMatchId(Long id);
}
