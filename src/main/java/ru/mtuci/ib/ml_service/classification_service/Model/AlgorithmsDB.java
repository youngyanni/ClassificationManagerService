package ru.mtuci.ib.ml_service.classification_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "algorithms_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmsDB {
    @Id
    @Column(name = "algId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "algorithmsName")
    private String algName;
    @ManyToOne
    @JoinColumn(name="providerId")
    ProviderDB providerDB;

}
