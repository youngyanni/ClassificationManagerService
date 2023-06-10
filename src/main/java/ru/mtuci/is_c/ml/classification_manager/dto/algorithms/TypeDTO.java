package ru.mtuci.is_c.ml.classification_manager.dto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumClosed;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TypeDTO {
    private String name;
    private int intTo;
    private int intFrom;
    private float floatTo;
    private float floatFrom;
    private EnumClosed closed;
    private String arrayType;
    private List<Object> dimensionArray;
    private String keyType;
    private String valueType;
    private List<Object> enumValues;
}
