package DBPostgres.repositories;


//import DBPostgres.models.Engineer;
import DBPostgres.models.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface EngRepository extends JpaRepository<Engineer, Integer> {
    Optional<Engineer> findByLogin(String login);

    long count();
}
