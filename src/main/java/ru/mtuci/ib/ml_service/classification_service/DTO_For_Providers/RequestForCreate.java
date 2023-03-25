package ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers;

import lombok.*;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.HyperParameter;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RequestForCreate {
    private List<HyperParameter> hyperParameters;
    private String tag;

}
