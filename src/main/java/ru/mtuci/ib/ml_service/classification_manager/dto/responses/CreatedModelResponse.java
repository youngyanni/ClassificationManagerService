package ru.mtuci.ib.ml_service.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatedModelResponse {
    private String name;
    private String modelLabel;
    private String metrics;

}
