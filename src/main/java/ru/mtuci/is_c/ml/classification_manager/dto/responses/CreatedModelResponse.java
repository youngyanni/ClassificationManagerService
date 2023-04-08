package ru.mtuci.is_c.ml.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatedModelResponse {
    private String name;
    private EnumLabels status;
    private String metrics;

}
