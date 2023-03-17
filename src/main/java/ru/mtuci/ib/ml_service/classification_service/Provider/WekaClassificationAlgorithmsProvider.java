package ru.mtuci.ib.ml_service.classification_service.Provider;

import ru.mtuci.ib.ml_service.classification_service.DTO_Response.HyperParameter;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.PredictionResponse;

import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

public class WekaClassificationAlgorithmsProvider implements InterfaceProvider {

    static List<String> wekaALg = Arrays.asList("J48","OneR","RandomForest");

    @Override
    public List<String> getAlg() {
        return wekaALg;
    }

    @Override
    public String getName() {
        return "Weka";
    }

    @Override
    public byte[] newModel(List<HyperParameter> hyperParam) {
        String inputString = "package";
        return inputString.getBytes();
    }

    @Override
    public Blob trainModel(Blob model) {
        return null;
    }

    @Override
    public PredictionResponse predictModel(Blob model, List<Object> matrixAttr) {
        double[] prediction = new double[]{3.56,1.13};
        List<String> labels = Arrays.asList("Yandex","HS");
        return new PredictionResponse(prediction,labels);
    }


}
