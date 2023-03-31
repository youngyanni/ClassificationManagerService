package ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers;

import lombok.*;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.Hyperparameter;
import ru.mtuci.ib.ml_service.classification_service.enums.EnumLabels;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GeneralRequestResponse {
    private EnumLabels modelLabel;
    private String modelId;
    private String classifier;
    private List<Hyperparameter> options;
    private List<List<Object>> features;
    private List<Object> labels;
    private SerializedCreatedModel model;
    private List<String> predictLabels;
    private double[] prediction;
    private String metrics;

}
