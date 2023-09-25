package ru.relex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.models.TgEngineerUser;

@Repository
public interface TgRepository extends JpaRepository<TgEngineerUser, Long> {
    TgEngineerUser findByTelegramUserId(Long id);
}
