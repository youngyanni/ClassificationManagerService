package ru.mtuci.ib.ml_service.classification_service.DTO_Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRegistration {
    private String name;
    private List<AlgorithmsDTO> algorithms;
    private String topic;
}
