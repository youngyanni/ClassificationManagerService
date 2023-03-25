package ru.mtuci.ib.ml_service.classification_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "provider_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDB {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "topic")
    private String topic;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AlgorithmsDB> alg;
}
