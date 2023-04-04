package ru.mtuci.is_c.ml.classification_manager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.SerializedCreatedModel;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.CreateModelRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.PredictionRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.TrainRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.AvailableAlgorithmsResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.CreatedModelResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.PredictionResponse;
import ru.mtuci.is_c.ml.classification_manager.model.ModelsDB;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;
import ru.mtuci.is_c.ml.classification_manager.repositories.AlgorithmsRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.ModelsRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.ProviderRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import ru.mtuci.is_c.ml.classification_manager.utils.MappingUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassificationManagerService {
    private final ModelsRepository modelsRepository;
    private final ProviderRepository providerRepository;
    private final AlgorithmsRepository algorithmsRepository;
    private final StreamBridge streamBridge;

    @Transactional
    public List<AvailableAlgorithmsResponse> availableAlgorithms() {
        return providerRepository.findAll()
                .stream()
                .map(MappingUtils::AlgorithmsEntityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CreatedModelResponse> getCreatedModels() {
        return modelsRepository.findAll()
                .stream()
                .map(entry -> CreatedModelResponse.builder()
                        .name(entry.getName())
                        .modelLabel(entry.getStatus().name())
                        .metrics(entry.getMetrics())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String createModel(CreateModelRequest param) {
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(param.getNameAlg()));
        var model = ModelsDB.builder()
                .name(param.getNameModel())
                .status(EnumLabels.SENT_FOR_CREATE)
                .algorithm(param.getNameAlg())
                .build();
        modelsRepository.save(model);
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .classifier(param.getNameAlg())
                .options(param.getHyperParam())
                .modelId(model.getId())
                .modelLabel(EnumLabels.CREATE)
                .build());
        return param.getNameModel();
    }

    @Transactional
    public String trainModel(TrainRequest param) throws Exception {
        var modelImpl = modelsRepository.findByName(param.getNameModel());
        System.out.println(modelImpl);
        if (modelImpl == null) {
            throw new Exception("Model not found");
        }
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(modelImpl.getAlgorithm()));
        System.out.println(topic);
        modelImpl.setStatus(EnumLabels.CREATED);
        modelsRepository.save(modelImpl);
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(SerializedCreatedModel.builder()
                        .createdModel(modelImpl.getModel().toString())
                        .build())
                .modelId(modelImpl.getId())
                .features(param.getFeatures())
                .labels(param.getLabels())
                .modelLabel(EnumLabels.TRAIN)
                .build());
        return EnumLabels.SENT_FOR_TRAIN.getDescription();
    }

    @Transactional
    public PredictionResponse predictModel(PredictionRequest param) throws Exception {
        var modelImpl = modelsRepository.findByName(param.getNameModel());
        if (modelImpl == null) {
            throw new Exception("Model not found");
        }
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(modelImpl.getAlgorithm()));
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(SerializedCreatedModel.builder()
                        .createdModel(modelImpl.getModel().toString())
                        .build())
                .modelId(modelImpl.getId())
                .features(param.getFeatures())
                .modelLabel(EnumLabels.PREDICT)
                .build());
        modelImpl = modelsRepository.findByName(param.getNameModel());
        return PredictionResponse.builder()
                .nameModel(param.getNameModel())
                .predict(modelImpl.getPredict())
                .build();
    }
}
