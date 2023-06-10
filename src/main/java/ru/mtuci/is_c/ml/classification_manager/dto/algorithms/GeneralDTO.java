package ru.mtuci.is_c.ml.classification_manager.dto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GeneralDTO {
    private String name;
    private List<ParameterDTO> parameters;
}
