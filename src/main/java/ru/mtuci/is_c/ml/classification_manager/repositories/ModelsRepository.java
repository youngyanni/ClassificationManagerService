package ru.mtuci.is_c.ml.classification_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.ModelsDB;

import java.util.UUID;

@Repository
public interface ModelsRepository extends JpaRepository<ModelsDB, UUID> {
    ModelsDB findByName(String Name);
}

