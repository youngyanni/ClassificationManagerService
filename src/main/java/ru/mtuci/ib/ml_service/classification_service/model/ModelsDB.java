package ru.mtuci.ib.ml_service.classification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Table(name = "models_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelsDB {
    @Id
    @Column(name = "Id",columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "Name")
    private String name;
    @Column(name = "model")
    @Lob
    private Blob model;
    @Column(name = "metrics")
    private String metrics;
    @Column(name = "status")
    private String status;
    @Column(name = "predict")
    private String predict;
    @Column(name = "algorithm")
    private String algorithm;
}
