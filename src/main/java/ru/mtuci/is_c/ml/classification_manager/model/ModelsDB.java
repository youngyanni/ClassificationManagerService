package ru.mtuci.is_c.ml.classification_manager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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
    @Column(name = "name",unique = true)
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
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "attribute",length = 1000)
    private String attribute;
}
