package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.ScalersDB;

@Repository
public interface ScalersRepository extends JpaRepository<ScalersDB, Long> {
    ScalersDB findByScalerName(String scalerName);
}
