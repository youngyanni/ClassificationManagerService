package ru.mtuci.ib.ml_service.classification_service.DTO_Response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Hyperparameter {
    private final String descriptionFlag;
    private final String description;
}
