package ru.mtuci.ib.ml_service.classification_service.DTO_Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PredictionRequest {
    private String nameModel;
    private List<List<Object>> features;
}
