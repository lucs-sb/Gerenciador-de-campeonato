package api.championship.manager.repositories;

import api.championship.manager.models.Championship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
//    @Query(value = "SELECT * FROM tb_championship " +
//            "WHERE user_id = :user_id", nativeQuery = true)
    Page<Championship> findByUserId(Long user_id, Pageable pageable);

    @Query(value = "SELECT * FROM tb_championship " +
            "WHERE user_id = :user_id AND " +
            "(status = :search OR number_of_teams = :search)", nativeQuery = true)
    List<Championship> findByparams(Long user_id, int search);

    @Query(value = "SELECT * FROM tb_championship " +
            "WHERE user_id = :user_id AND " +
            "(name like %:search% OR award like %:search% OR description like %:search%)", nativeQuery = true)
    List<Championship> findByUserAndName(Long user_id, String search);
}
