package Boot.cadastreCompany.repositories;

import Boot.cadastreCompany.models.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngRepository extends JpaRepository<Engineer, Integer> {
    Optional<Engineer> findByLogin(String login);
}
