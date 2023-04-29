package ru.mtuci.is_c.ml.classification_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "algorithms_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmsDB {
    @Id
    @Column(name = "alg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "algorithms_name")
    private String algName;
    @ManyToOne
    @MapsId("providerdb_id")
    @JsonBackReference
    private ProviderDB providerDB;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "algorithmsdb_alg_id")
    @JsonManagedReference
    private List<HyperparametersDB> hyperparametersDBList = new ArrayList<HyperparametersDB>();
}
