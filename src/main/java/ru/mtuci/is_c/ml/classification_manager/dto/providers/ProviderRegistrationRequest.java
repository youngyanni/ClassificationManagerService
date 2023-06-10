package ru.mtuci.is_c.ml.classification_manager.dto.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.GeneralDTO;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProviderRegistrationRequest {
    @NonNull
    private String provider;
    private List<GeneralDTO> algorithms;
    private List<GeneralDTO> scalers;
    private List<GeneralDTO> encoders;
    @NonNull
    private String topic;
}
