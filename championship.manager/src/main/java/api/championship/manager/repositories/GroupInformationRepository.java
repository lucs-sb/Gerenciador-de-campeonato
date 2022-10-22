package api.championship.manager.repositories;

import api.championship.manager.models.GroupInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupInformationRepository extends JpaRepository<GroupInformation, Long> {
}
