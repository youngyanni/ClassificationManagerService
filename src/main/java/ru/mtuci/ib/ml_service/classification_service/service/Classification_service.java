package ru.mtuci.ib.ml_service.classification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.GeneralRequestResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.SerializedCreatedModel;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.CreateModelRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.PredictionRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.TrainRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.*;
import ru.mtuci.ib.ml_service.classification_service.model.ModelsDB;
import ru.mtuci.ib.ml_service.classification_service.enums.EnumLabels;
import ru.mtuci.ib.ml_service.classification_service.repositories.AlgorithmsRepository;
import ru.mtuci.ib.ml_service.classification_service.repositories.ModelsRepository;
import ru.mtuci.ib.ml_service.classification_service.repositories.ProviderRepository;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Component
public class Classification_service {
    private final ModelsRepository modelsRepository;
    private final ProviderRepository providerRepository;
    private final AlgorithmsRepository algorithmsRepository;
    private final StreamBridge streamBridge;

    public List<AvailableAlgorithmsResponse> availableAlgorithms() {
        return providerRepository.findAll()
                .stream()
                .map(entry -> AvailableAlgorithmsResponse.builder()
                        .poroviderName(entry.getName())
                        .algList(entry.getAlg()
                                .stream()
                                .map(alg -> AlgorithmsDTO.builder()
                                        .algName(alg.getAlgName())
                                        .hyperparameters(alg.getHyperparametersDBList()
                                                .stream()
                                                .map(hyperparam -> Hyperparameter.builder()
                                                        .descriptionFlag(hyperparam.getFlag())
                                                        .description(hyperparam.getDescription())
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<CreatedModelResponse> getCreatedModels() {
        return modelsRepository.findAll()
                .stream()
                .map(entry -> CreatedModelResponse.builder()
                        .name(entry.getName())
                        .modelLabel(entry.getStatus())
                        .metrics(entry.getMetrics())
                        .build())
                .collect(Collectors.toList());
    }

    public String createModel(CreateModelRequest param) {
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(param.getNameAlg()));
        modelsRepository.save(ModelsDB.builder()
                .name(param.getNameModel())
                .status(EnumLabels.SENT_FOR_CREATE.getDescription())
                .algorithm(param.getNameAlg())
                .build());
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .classifier(param.getNameAlg())
                .options(param.getHyperParam())
                .modelId(param.getNameModel())
                .modelLabel(EnumLabels.CREATE)
                .build());
        return param.getNameModel();
    }

    public String trainModel(TrainRequest param) throws Exception {
        final var modelImpl = modelsRepository.findModelByName(param.getNameModel());
        if (modelImpl == null) {
            log.error("Error: {}",modelImpl );
            throw new Exception("Model not found");
        }
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(modelImpl.getAlgorithm()));
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(SerializedCreatedModel.builder()
                        .createdModel(Arrays.toString(modelImpl.getModel()
                                .getBytes(1L, (int) modelImpl.getModel().length())))
                        .build())
                .modelId(modelImpl.getName())
                .features(param.getFeatures())
                .labels(param.getLabels())
                .modelLabel(EnumLabels.TRAIN)
                .build());
        modelImpl.setStatus(EnumLabels.SENT_FOR_TRAIN.getDescription());
        modelsRepository.save(modelImpl);
        return EnumLabels.SENT_FOR_CREATE.getDescription();
    }

    public PredictionResponse predictModel(PredictionRequest param) throws Exception {
        var modelImpl = modelsRepository.findModelByName(param.getNameModel());
        if (modelImpl == null) {
            throw new Exception("Model not found");
        }
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(modelImpl.getAlgorithm()));
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(SerializedCreatedModel.builder()
                        .createdModel(Arrays.toString(modelImpl.getModel()
                                .getBytes(1L, (int) modelImpl.getModel().length())))
                        .build())
                .modelId(modelImpl.getName())
                .features(param.getFeatures())
                .modelLabel(EnumLabels.PREDICT)
                .build());
        return PredictionResponse.builder()
                .nameModel(param.getNameModel())
                .predict(modelImpl.getPredict())
                .build();
    }
}
