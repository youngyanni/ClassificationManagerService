package ru.mtuci.ib.ml_service.classification_service.DTO_Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.Hyperparameter;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelRequest {
    private String nameModel;
    private String nameAlg;
    private List<Hyperparameter> hyperParam;
}
