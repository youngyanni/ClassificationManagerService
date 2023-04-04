package ru.mtuci.is_c.ml.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.AlgorithmsDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableAlgorithmsResponse {
    private String poroviderName;
    private List<AlgorithmsDTO> algList;

}
