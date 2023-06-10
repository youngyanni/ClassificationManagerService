package ru.mtuci.is_c.ml.classification_manager.dto.providers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GeneralObjectStructure {
    private String name;
    private List<Parameters> parameters;
    private byte[] serializedData;
}
