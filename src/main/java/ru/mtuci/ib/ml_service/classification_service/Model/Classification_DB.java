package ru.mtuci.ib.ml_service.classification_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;


@Entity
@Data
@Table(name = "classification_bd")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class Classification_DB {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "precision")
    private double Precision;

    @Column(name = "recall")
    private double Recall;
    @Column(name = "model")
    @Lob
    private Blob Model;
    @Column(name = "alg")
    private String nameAlg;
    @Column(name = "metrics")
    private String metrics;
}
