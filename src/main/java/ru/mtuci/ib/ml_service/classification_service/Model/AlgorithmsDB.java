package ru.mtuci.ib.ml_service.classification_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "algorithms_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmsDB {
    @Id
    @Column(name = "alg_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long algId;
    @Column(name = "algorithms_name")
    private String algName;
    @ManyToOne
    @MapsId("providerdb_id")
    private ProviderDB providerDB;
}
