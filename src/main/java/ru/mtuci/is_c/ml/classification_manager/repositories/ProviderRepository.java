package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.ProviderDB;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderDB, Long> {
    @Query(value = "select provider_table.topic from provider_table where provider_table.id=?1", nativeQuery = true)
    String findTopic(int Id);
}
