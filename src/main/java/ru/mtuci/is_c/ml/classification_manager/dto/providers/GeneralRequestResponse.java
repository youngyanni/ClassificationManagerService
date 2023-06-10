package ru.mtuci.is_c.ml.classification_manager.dto.providers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.encoders.Feature;
import ru.mtuci.is_c.ml.classification_manager.dto.encoders.FeatureEncoderStructure;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GeneralRequestResponse {
    private String modelId;
    private EnumLabels modelLabel;
    private List<Object> features;
    private List<Feature> featuresHeader;
    private List<Object> labels;
    private List<Map<String, Double>> distributions;
    private double[] metrics;
    private GeneralObjectStructure model;
    private List<GeneralObjectStructure> scalers;
    private List<FeatureEncoderStructure> encodersFeatures;
    private GeneralObjectStructure encoderLabels;
}
