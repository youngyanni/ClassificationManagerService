package ru.mtuci.ib.ml_service.classification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;


@Entity
@Data
@Table(name = "models_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelsDB {
    @Id
    @Column(name = "Id")
    @GeneratedValue()
    private Long id;
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