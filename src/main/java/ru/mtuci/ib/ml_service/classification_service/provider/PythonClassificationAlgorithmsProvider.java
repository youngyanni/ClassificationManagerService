package ru.mtuci.ib.ml_service.classification_service.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.GeneralRequestResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.SerializedCreatedModel;
import ru.mtuci.ib.ml_service.classification_service.enums.EnumLabels;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

@Component
public class PythonClassificationAlgorithmsProvider {

    @Bean
    public Function<GeneralRequestResponse, GeneralRequestResponse> newModel() {
        return model -> {
            byte[] inputString = "package coxgdfgdfgfgf".getBytes();
            return GeneralRequestResponse.builder()
                    .modelLabel(EnumLabels.CREATED)
                    .model(new SerializedCreatedModel(Base64.getEncoder().encodeToString(inputString)))
                    .modelId(model.getModelId())
                    .build();
        };
    }

    @Bean
    public Function<GeneralRequestResponse, GeneralRequestResponse> trainingModel() {
        return model -> {
            byte[] inputString = "package sdgsdgs".getBytes();
            return GeneralRequestResponse.builder()
                    .modelLabel(EnumLabels.TRAINED)
                    .model(new SerializedCreatedModel(Base64.getEncoder().encodeToString(inputString)))
                    .modelId(model.getModelId())
                    .build();
        };
    }

    @Bean
    public Function<GeneralRequestResponse, GeneralRequestResponse> predictModel() {
        return model -> {
            double[] pred = new double[]{123, 213, 434, 5324};
            List<String> labels = new ArrayList<>();
            labels.add("Yandex");
            labels.add("Hs");
            return GeneralRequestResponse.builder()
                    .modelLabel(EnumLabels.PREDICTED)
                    .modelId(model.getModelId())
                    .predictLabels(labels)
                    .prediction(pred)
                    .build();
        };
    }
}
