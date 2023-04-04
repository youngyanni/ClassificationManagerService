package ru.mtuci.ib.ml_service.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.ib.ml_service.classification_manager.dto.responses.AttributeResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainResponse {
    private String prediction;
    private String evaluation;
    private double[] probabilities;
    private List<AttributeResponse> attributes;

}
