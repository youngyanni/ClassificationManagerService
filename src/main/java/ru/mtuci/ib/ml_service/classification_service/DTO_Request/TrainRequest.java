package ru.mtuci.ib.ml_service.classification_service.DTO_Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainRequest {
    private String nameModel;
    private List<Object> matrixAttrTrain;
    private List<Object> matrixAttrTest;
    private List<String> labels;

}
