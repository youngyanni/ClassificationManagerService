package ru.mtuci.ib.ml_service.classification_service.DTO_Response;

import java.util.List;

public interface CreatedModelResponse {
    String getName();
    List<String> getMetrics();

}
