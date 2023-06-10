package ru.mtuci.is_c.ml.classification_manager.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class ValidationResult {
    private final boolean result;
    private final Object outOfRangeValue;
    private final String flag;
}
