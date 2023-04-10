package ru.mtuci.is_c.ml.classification_manager.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelNotFoundException extends RuntimeException {
    private String modelName;
}
