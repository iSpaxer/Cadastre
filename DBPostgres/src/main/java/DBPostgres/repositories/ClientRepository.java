package DBPostgres.repositories;


import DBPostgres.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findFirstByOrderByIdDesc();
    Page<Client> findAll(Pageable pageable);


    @Query(value = "SELECT * FROM client WHERE CAST(created_data AS DATE) BETWEEN :fromDate AND :toDate ",
            nativeQuery = true)
    Page<Client> findAllClientsWithBetweenDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, Pageable pageable);

    @Query(value = "SELECT * FROM client WHERE eng_id IS NOT NULL",
            nativeQuery = true)
    Page<Client> findEndedClients(Pageable pageable);

    @Query(value = "SELECT * FROM client WHERE eng_id IS NULL",
            nativeQuery = true)
    Page<Client> findActiveClients(Pageable pageable);


//    Optional<Client> findBy ///TODO findById
}
