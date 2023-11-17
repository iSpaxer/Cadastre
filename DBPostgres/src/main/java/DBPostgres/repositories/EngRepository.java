package DBPostgres.repositories;


//import DBPostgres.models.Engineer;
import DBPostgres.models.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngRepository extends JpaRepository<Engineer, Integer> {
    Optional<Engineer> findByLogin(String login);

    long count();

    Optional<Engineer> findByTgId(Long tgId);

    @Query(value = "SELECT tg_id FROM engineer WHERE tg_id IS NOT NULL",
            nativeQuery = true)
    Optional<Long[]> findAllEngineersWithTgId();
}
