package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.IntFloatDB;



@Repository
public interface IntFloatRepository extends JpaRepository<IntFloatDB, Long> {
    @Query(value = "SELECT i.* from int_float_table i WHERE i.id=:id",nativeQuery = true)
    IntFloatDB foundRangeById(@Param("id")Long id);
}
