package ru.mtuci.is_c.ml.classification_manager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateNameError extends RuntimeException {
    private String modelName;
}
