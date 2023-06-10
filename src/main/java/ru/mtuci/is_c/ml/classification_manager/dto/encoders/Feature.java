package ru.mtuci.is_c.ml.classification_manager.dto.encoders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Feature {
    private String name;
    private String type;
}
