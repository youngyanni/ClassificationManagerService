package ru.mtuci.ib.ml_service.classification_manager.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.ib.ml_service.classification_manager.dto.algorithms.HyperparameterDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelRequest {
    private String nameModel;
    private String nameAlg;
    private List<HyperparameterDTO> hyperParam;
}
