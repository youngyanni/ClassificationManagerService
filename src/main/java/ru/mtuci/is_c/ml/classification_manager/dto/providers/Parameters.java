package ru.mtuci.is_c.ml.classification_manager.dto.providers;


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
public class Parameters {
    private String name;
    private Object value;
}
