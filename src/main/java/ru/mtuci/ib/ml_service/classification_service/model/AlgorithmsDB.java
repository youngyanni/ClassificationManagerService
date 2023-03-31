package ru.mtuci.ib.ml_service.classification_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "algorithms_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmsDB {
    @Id
    @Column(name = "alg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "algorithms_name")
    private String algName;
    @ManyToOne
    @MapsId("providerdb_id")
    @JsonBackReference
    private ProviderDB providerDB;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "algorithmsdb_alg_id")
    @JsonManagedReference
    private List<HyperparametersDB> hyperparametersDBList = new ArrayList<HyperparametersDB>();
}
