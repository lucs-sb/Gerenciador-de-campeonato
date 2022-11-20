package api.championship.manager.repositories;

import api.championship.manager.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(value = "SELECT * FROM tb_group " +
            "WHERE championship_id = :championshipId AND deletion_date IS NULL", nativeQuery = true)
    List<Group> findGroupsByChampionshipId(Long championshipId);
}
