package ru.mtuci.ib.ml_service.classification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_service.model.ModelsDB;


@Repository
public interface ModelsRepository extends JpaRepository<ModelsDB, Long> {
    @Query(value = "select * from models_table where models_table.name=?1", nativeQuery = true)
    ModelsDB findByName(String Name);

    ModelsDB findModelByName(String tag);
}

