package ru.mtuci.ib.ml_service.classification_service.Provider;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.RequestForCreate;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.RequestForPredict;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.RequestForTrain;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.ResponseForSave;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.HyperParameter;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.PredictionResponse;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class PythonClassificationAlgorithmsProvider {

   @Bean
    public Function<RequestForCreate,ResponseForSave> newModel() {
        return model -> {
            String inputString = "package coxgdfgdfgfgf";
            return new ResponseForSave(inputString.getBytes(),"created");
        };
    }
    @Bean
    public Function<RequestForTrain,ResponseForSave> trainingModel() {
        return model -> {
            String inputString = "package sdffd";
            return new ResponseForSave(inputString.getBytes(),"trained");
        };
    }
    @Bean
    public Function<RequestForPredict,PredictionResponse> predictModel() {
        return model -> {
            double[] pred = new double[]{123,213,434,5324};
            List<String> labels = new ArrayList<>();
            labels.add("Yandex");
            labels.add("Hs");
            return new PredictionResponse(pred,labels,"predicted");
        };
    }
}
