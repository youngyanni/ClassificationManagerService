package ru.mtuci.is_c.ml.classification_manager.dto.providers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SerializedCreatedModel {
    private byte[] model;
    private String attribute;
    private List<Double> labels;
    private List<Map<Double, Double>> distributions;
}
