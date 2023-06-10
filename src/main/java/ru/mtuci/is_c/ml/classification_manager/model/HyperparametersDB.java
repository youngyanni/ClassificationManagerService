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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Table(name = "hyperparameters_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class HyperparametersDB {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flag")
    private String flag;

    @Column(name = "description",columnDefinition = "VARCHAR")
    private String description;

    @Column(name = "required")
    private boolean required;

    @ManyToOne
    @MapsId("algorithmId")
    @JsonBackReference
    private AlgorithmsDB algorithmsDB;

    @ManyToOne
    @MapsId("encoderId")
    @JsonBackReference
    private EncodersDB encodersDB;

    @ManyToOne
    @MapsId("scalersId")
    @JsonBackReference
    private ScalersDB scalersDB;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "intfloatdb_id", referencedColumnName = "id")
    private IntFloatDB intFloatRange;

    @OneToOne(mappedBy = "hyperparametersDB", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private DictDB dictRange;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hyperparametersdb_id")
    @JsonManagedReference
    private List<EnumDB> enumRange = new ArrayList<EnumDB>();
}
