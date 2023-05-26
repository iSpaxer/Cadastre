package Boot.cadastreCompany.repositories;

import Boot.cadastreCompany.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Client, Integer> {

}
