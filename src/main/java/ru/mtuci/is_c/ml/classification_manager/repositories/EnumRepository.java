package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.EnumDB;

import java.util.List;

@Repository
public interface EnumRepository extends JpaRepository<EnumDB, Long> {
    @Query(value = "SELECT en.* from enum_table en WHERE en.hyperparametersdb_id=:id",nativeQuery = true)
    List<EnumDB> findByHyperparametersDB_Id(Long id);
}
