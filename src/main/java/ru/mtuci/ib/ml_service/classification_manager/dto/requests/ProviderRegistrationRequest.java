package ru.mtuci.ib.ml_service.classification_manager.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.ib.ml_service.classification_manager.dto.algorithms.AlgorithmsDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRegistrationRequest {
    private String providerName;
    private List<AlgorithmsDTO> algorithms;
    private String topicName;
}
