package ru.mtuci.is_c.ml.classification_manager.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.AlgorithmsDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRegistrationRequest {
    private String providerName;
    private List<AlgorithmsDTO> algorithms;
    private String topicName;
}
