package ru.mtuci.ib.ml_service.classification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_service.Model.ProviderDB;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderDB, Long> {
    @Query(value = "select provider_table.topic from provider_table where provider_table.name=?1",nativeQuery = true)
    String getTopic(String providerName);

    ProviderDB findByName(String Name);

}
