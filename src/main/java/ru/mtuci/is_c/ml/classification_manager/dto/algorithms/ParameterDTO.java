package ru.mtuci.is_c.ml.classification_manager.dto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ParameterDTO {
    private String flag;
    private String description;
    private boolean required;
    private TypeDTO type;
}
