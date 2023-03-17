package ru.mtuci.ib.ml_service.classification_service.Provider;

import ru.mtuci.ib.ml_service.classification_service.DTO_Response.HyperParameter;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.PredictionResponse;

import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

public class PythonClassificationAlgorithmsProvider implements InterfaceProvider{
    static List<String> pythonALg = Arrays.asList("Alg1","Alg2","Alg3");

    @Override
    public List<String> getAlg() {
        return pythonALg;
    }

    @Override
    public String getName() {
        return "Python";
    }

    @Override
    public byte[] newModel(List<HyperParameter> hyperParam) {
        String inputString = "package coxgdfgdfgfgf";
        return inputString.getBytes();
    }

    @Override
    public Blob trainModel(Blob model) {
        return null;
    }

    @Override
    public PredictionResponse predictModel(Blob model, List<Object> matrixAttr) {
        double[] prediction = new double[]{1.23,2.23};
        List<String> labels = Arrays.asList("Google","Hearthstone");
        return new PredictionResponse(prediction,labels);
    }
}
