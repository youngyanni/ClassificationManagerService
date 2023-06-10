package ru.mtuci.is_c.ml.classification_manager.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;


import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "model")
    private byte[] model;
    @Column(name = "metrics")
    private String metrics;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumLabels status;
    @Column(name = "algorithm")
    private String algorithm;
    @Column(name ="predict_label")
    private String predictLabel;
    @Column(name = "distributions")
    private String distributions;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "hyperparams")
    private String hyperparams;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "modelsdb_id")
    private List<PreproccesingDataDB> preproccesingData = new ArrayList<PreproccesingDataDB>();
}
