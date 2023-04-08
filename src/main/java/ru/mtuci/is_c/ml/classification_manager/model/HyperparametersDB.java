package ru.mtuci.is_c.ml.classification_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
    private String descriptionFlag;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @MapsId("algorithmId")
    @JsonBackReference
    private AlgorithmsDB algorithmsDB;

}
