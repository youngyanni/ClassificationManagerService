package ru.mtuci.ib.ml_service.classification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_service.model.AlgorithmsDB;

@Repository
public interface AlgorithmsRepository extends JpaRepository<AlgorithmsDB, Long> {
    @Query(value = "select algorithms_table.providerdb_id from algorithms_table where algorithms_table.algorithms_name=?1", nativeQuery = true)
    int findIdByAlgName(String nameAlg);
}
