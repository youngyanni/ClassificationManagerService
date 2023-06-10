package ru.mtuci.is_c.ml.classification_manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Table(name = "provider_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProvidersDB {
    @Id
    @Column(name = "id")
    @GeneratedValue()
    private Long providerId;
    @Column(name = "name")
    private String providerName;
    @Column(name = "topic")
    private String topic;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "providerdb_id")
    @JsonManagedReference
    private List<AlgorithmsDB> alg = new ArrayList<AlgorithmsDB>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "providerdb_id")
    @JsonManagedReference
    private List<EncodersDB> endcoder = new ArrayList<EncodersDB>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "providerdb_id")
    @JsonManagedReference
    private List<ScalersDB> scaler = new ArrayList<ScalersDB>();
}
