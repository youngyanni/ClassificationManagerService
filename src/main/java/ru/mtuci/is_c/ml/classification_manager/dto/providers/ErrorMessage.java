package ru.mtuci.is_c.ml.classification_manager.dto.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String modelId;
    private String errorType;
    private String errorMessage;
    private LocalDateTime localDateTime;
    private EnumLabels Stage;

}

