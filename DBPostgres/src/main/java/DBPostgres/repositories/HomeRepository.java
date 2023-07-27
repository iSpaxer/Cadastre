package DBPostgres.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utils.models.Client;

@Repository
public interface HomeRepository extends JpaRepository<Client, Integer> {

}
