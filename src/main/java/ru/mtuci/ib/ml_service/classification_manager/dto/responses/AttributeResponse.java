package ru.mtuci.ib.ml_service.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse {
    private String name;
    private String type;
    private List<String> nominalValues;
}

