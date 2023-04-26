package ru.mtuci.is_c.ml.classification_manager.dto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlgorithmsDTO {
    private String algorithmName;
    private List<HyperparameterDTO> hyperparameters;
}
