package ru.mtuci.is_c.ml.classification_manager.dto.requests;

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
