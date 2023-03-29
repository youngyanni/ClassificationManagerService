package ru.mtuci.ib.ml_service.classification_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    private String name;
    @Column(name = "topic")
    private String topic;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "providerdb_id")
    private List<AlgorithmsDB> alg=new ArrayList<AlgorithmsDB>();

}
