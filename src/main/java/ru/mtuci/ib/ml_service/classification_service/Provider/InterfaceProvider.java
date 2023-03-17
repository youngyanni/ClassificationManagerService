package ru.mtuci.ib.ml_service.classification_service.Provider;

import ru.mtuci.ib.ml_service.classification_service.DTO_Response.HyperParameter;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.PredictionResponse;

import java.sql.Blob;
import java.util.List;

public interface InterfaceProvider {
    List<String> getAlg();
    String getName();
    byte[] newModel(List<HyperParameter> hyperParam);
    Blob trainModel(Blob model);
    PredictionResponse predictModel(Blob model, List<Object> matrixAttr);

}
