package ru.mtuci.is_c.ml.classification_manager.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.encoders.Feature;
import ru.mtuci.is_c.ml.classification_manager.dto.encoders.FeatureEncoderStructure;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralObjectStructure;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainPredictRequest {
    private String modelName;
    private List<Object> features;
    private List<Feature> featuresHeader;
    private List<Object> labels;
    private List<FeatureEncoderStructure> encoderFeatures;
    private GeneralObjectStructure encoderLabels;
    private List<GeneralObjectStructure> scalers;
}
