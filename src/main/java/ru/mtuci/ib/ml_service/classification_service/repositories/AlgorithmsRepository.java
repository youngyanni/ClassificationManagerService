package ru.mtuci.ib.ml_service.classification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_service.Model.AlgorithmsDB;

@Repository
public interface AlgorithmsRepository extends JpaRepository<AlgorithmsDB, Long> {

}
