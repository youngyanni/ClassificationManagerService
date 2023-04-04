package ru.mtuci.ib.ml_service.classification_manager.dto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.ib.ml_service.classification_manager.dto.algorithms.HyperparameterDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlgorithmsDTO {
    private String algName;
    private List<HyperparameterDTO> hyperparameterRespons;
}
