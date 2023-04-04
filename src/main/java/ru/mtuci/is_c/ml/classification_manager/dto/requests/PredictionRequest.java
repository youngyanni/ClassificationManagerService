package ru.mtuci.is_c.ml.classification_manager.dto.requests;

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
