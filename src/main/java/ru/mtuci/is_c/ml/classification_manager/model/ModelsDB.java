package ru.mtuci.is_c.ml.classification_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;


import java.sql.Blob;
import java.util.UUID;


@Entity
@Data
@Table(name = "models_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelsDB {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "Name",unique = true)
    private String name;
    @Column(name = "model")
    @Lob
    private Blob model;
    @Column(name = "metrics")
    private String metrics;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumLabels status;
    @Column(name = "predict")
    private String predict;
    @Column(name = "algorithm")
    private String algorithm;
}
