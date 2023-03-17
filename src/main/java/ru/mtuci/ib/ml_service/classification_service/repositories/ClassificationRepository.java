package ru.mtuci.ib.ml_service.classification_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.CreatedModelResponse;
import ru.mtuci.ib.ml_service.classification_service.Model.Classification_DB;

import java.util.List;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification_DB, Long> {
    @Query(value = "select classification_bd.name, classification_bd.metrics from classification_bd",nativeQuery=true)
    List<CreatedModelResponse> getAllModels();
    @Query(value ="select classification_bd.Id from classification_bd where classification_bd.model =?1",nativeQuery=true)
    Integer getIdByModel(String model);
    @Query(value = "select * from classification_bd where classification_bd.Name=?1",nativeQuery=true)
    Classification_DB findByName(String Name);
}

