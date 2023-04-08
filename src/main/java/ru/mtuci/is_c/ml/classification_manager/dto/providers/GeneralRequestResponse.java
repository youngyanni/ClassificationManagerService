package ru.mtuci.is_c.ml.classification_manager.dto.providers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.HyperparameterDTO;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GeneralRequestResponse {
    private EnumLabels modelLabel;
    private String modelId;
    private String classifier;
    private List<HyperparameterDTO> options;
    private List<List<Object>> features;
    private List<Object> labels;
    private SerializedCreatedModel model;
    private List<String> predictLabels;
    private double[] prediction;
    private String metrics;

}
