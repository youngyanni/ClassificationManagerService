package ru.mtuci.ib.ml_service.classification_manager.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.mtuci.ib.ml_service.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.ib.ml_service.classification_manager.dto.providers.SerializedCreatedModel;
import ru.mtuci.ib.ml_service.classification_manager.enums.EnumLabels;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

@Component
public class PythonClassificationAlgorithmsProvider {

    @Bean
    public Function<GeneralRequestResponse, GeneralRequestResponse> newModel() {
        return model -> {
            System.out.println("Создание модели");
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
            System.out.println("Обучение модели");
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
            System.out.println("предикт");
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
