package ru.mtuci.ib.ml_service.classification_manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table(name = "provider_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProviderDB {
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

}
