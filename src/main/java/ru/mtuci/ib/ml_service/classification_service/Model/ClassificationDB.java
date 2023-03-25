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
public class ClassificationDB {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "provider")
    private String provider;
}
