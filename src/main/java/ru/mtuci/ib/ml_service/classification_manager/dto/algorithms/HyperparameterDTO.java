package ru.mtuci.ib.ml_service.classification_manager.dto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HyperparameterDTO {
    private String descriptionFlag;
    private String description;
}
