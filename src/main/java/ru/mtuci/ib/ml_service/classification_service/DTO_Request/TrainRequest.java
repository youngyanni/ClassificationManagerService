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
    private List<List<Object>> features;
    private List<Object> labels;

}
