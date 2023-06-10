package ru.mtuci.is_c.ml.classification_manager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@Table(name = "preproccesing_data_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreproccesingDataDB {
    @Id
    @Column(name = "id")
    @GeneratedValue()
    private Long id;
    @Column(name = "data")
    private byte[] data;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "parameters")
    private String parameters;
    @Column (name="feature_name")
    private String featureName;
    @Column (name = "stage")
    private String stage;
    @ManyToOne
    @MapsId("modelsdb_id")
    private ModelsDB modelsDB;
}
