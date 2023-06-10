package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.DictDB;

@Repository
public interface DictionaryRepository extends JpaRepository<DictDB,Long> {
}
