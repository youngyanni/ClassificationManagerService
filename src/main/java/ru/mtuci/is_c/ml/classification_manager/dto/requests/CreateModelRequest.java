package ru.mtuci.is_c.ml.classification_manager.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.Hyperparameters;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelRequest {
    private String nameModel;
    private String nameAlg;
    private List<Hyperparameters> hyperParam;
}
