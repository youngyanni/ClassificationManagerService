package ru.mtuci.is_c.ml.classification_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumClosed;

@Table(name = "int_float_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class IntFloatDB {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "int_to")
    private int intTo;
    @Column(name = "int_from")
    private int intFrom;
    @Column(name = "float_to")
    private float floatTo;
    @Column(name = "float_from")
    private float floatFrom;
    @Column(name = "closed")
    private EnumClosed closed;
    @OneToOne(mappedBy = "intFloatRange")
    private HyperparametersDB hyperparametersDB;
}
