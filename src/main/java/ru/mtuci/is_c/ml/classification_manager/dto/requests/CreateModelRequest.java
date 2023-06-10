package ru.mtuci.is_c.ml.classification_manager.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.Parameters;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateModelRequest {
    private String nameModel;
    private String nameAlg;
    private List<Parameters> hyperparams;
}
