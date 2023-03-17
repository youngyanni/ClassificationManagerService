package ru.mtuci.ib.ml_service.classification_service.DTO_Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
