package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.AlgorithmsDB;

@Repository
public interface AlgorithmsRepository extends JpaRepository<AlgorithmsDB, Long> {
    @Query(value = "select algorithms_table.providerdb_id from algorithms_table where algorithms_table.name=?1", nativeQuery = true)
    int findIdByAlgName(String nameAlg);
    @Query(value = "SELECT * FROM algorithms_table WHERE name = :name", nativeQuery = true)
    AlgorithmsDB findByName(String name);
}
