package ru.mtuci.ib.ml_service.classification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_service.Model.ClassificationDB;


@Repository
public interface ClassificationRepository extends JpaRepository<ClassificationDB, Long> {
    @Query(value = "select classification_bd.Id from classification_bd where classification_bd.model =?1", nativeQuery = true)
    Integer getIdByModel(String model);

    @Query(value = "select * from classification_bd where classification_bd.name=?1", nativeQuery = true)
    ClassificationDB findByName(String Name);
}

