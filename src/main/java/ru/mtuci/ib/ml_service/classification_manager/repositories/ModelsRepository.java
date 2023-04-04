package ru.mtuci.ib.ml_service.classification_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.ib.ml_service.classification_manager.model.ModelsDB;

import java.util.UUID;

@Repository
public interface ModelsRepository extends JpaRepository<ModelsDB, UUID> {
    ModelsDB findByName(String Name);
}

