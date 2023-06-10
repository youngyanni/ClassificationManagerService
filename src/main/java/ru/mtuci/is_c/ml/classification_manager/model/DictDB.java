package ru.mtuci.is_c.ml.classification_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "dictionary_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DictDB {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "key_type")
    private String keyType;
    @Column(name = "value_type")
    private String valueType;
    @OneToOne
    @JoinColumn(name = "hyperparametersdb_id")
    @JsonBackReference
    private HyperparametersDB hyperparametersDB;
}
