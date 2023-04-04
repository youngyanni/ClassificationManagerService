package ru.mtuci.ib.ml_service.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.ib.ml_service.classification_manager.dto.algorithms.AlgorithmsDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableAlgorithmsResponse {
    private String poroviderName;
    private List<AlgorithmsDTO> algList;

}
