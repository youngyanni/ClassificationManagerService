package ru.mtuci.ib.ml_service.classification_service.DTO_Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HyperParameter {
    private String name;
    private String value;

}
