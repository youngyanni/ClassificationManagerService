package ru.mtuci.ib.ml_service.classification_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "hyperparameters_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HyperparametersDB {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flag")
    private String flag;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @MapsId("algorithmId")
    @JsonBackReference
    private AlgorithmsDB algorithmsDB;

}
