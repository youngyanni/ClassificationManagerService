package ru.mtuci.is_c.ml.classification_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mtuci.is_c.ml.classification_manager.model.HyperparametersDB;

@Repository
public interface HyperparameteresRepository extends JpaRepository<HyperparametersDB, Long> {
    @Query(value = "SELECT h.* FROM hyperparameters_table h LEFT JOIN algorithms_table a ON h.algorithmsdb_alg_id = a.alg_id LEFT JOIN scalers_table s ON h.scalersdb_scaler_id = s.scaler_id" +
            " LEFT JOIN encoders_table e ON h.encodersdb_encoder_id = e.encoder_id " +
            "WHERE h.flag =:flag" +
            "  AND (" +
            "            a.name =:dataName" +
            "        OR s.scalers_name =:dataName" +
            "        OR e.encoder_name = :dataName" +
            "    )",nativeQuery = true)
    HyperparametersDB foundRange(@Param("flag") String flag, @Param("dataName") String dataName);
}
