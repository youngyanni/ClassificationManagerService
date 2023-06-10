package ru.mtuci.is_c.ml.classification_manager.dto.encoders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralObjectStructure;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FeatureEncoderStructure {
    private String featureName;
    private GeneralObjectStructure encoder;
}
