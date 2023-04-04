package ru.mtuci.is_c.ml.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionResponse {
    private String nameModel;
    private String predict;
}
