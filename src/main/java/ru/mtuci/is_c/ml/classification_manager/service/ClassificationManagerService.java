package ru.mtuci.is_c.ml.classification_manager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralObjectStructure;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.TrainPredictRequest;
import ru.mtuci.is_c.ml.classification_manager.exception.DataProccessingToolsNotFound;
import ru.mtuci.is_c.ml.classification_manager.exception.DuplicateNameError;
import ru.mtuci.is_c.ml.classification_manager.exception.ModelNotFoundException;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.CreateModelRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.AvailableDataProccesingToolsEntityToDTOResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.CreatedModelResponse;
import ru.mtuci.is_c.ml.classification_manager.model.AlgorithmsDB;
import ru.mtuci.is_c.ml.classification_manager.model.ModelsDB;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;
import ru.mtuci.is_c.ml.classification_manager.repositories.AlgorithmsRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.ModelsRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.ProviderRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import ru.mtuci.is_c.ml.classification_manager.utils.CheckParamValues;
import ru.mtuci.is_c.ml.classification_manager.utils.MappingUtils;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassificationManagerService {
    private final ModelsRepository modelsRepository;
    private final ProviderRepository providerRepository;
    private final AlgorithmsRepository algorithmsRepository;
    private final StreamBridge streamBridge;
    private final CheckParamValues checkParamValues;
    private final MappingUtils mappingUtils;

    @Transactional
    public List<AvailableDataProccesingToolsEntityToDTOResponse> dataProcessingToolsInfo() {
        return providerRepository.findAll()
                .stream()
                .map(MappingUtils::DataProccesingToolsEntityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CreatedModelResponse> getCreatedModels() {
        return modelsRepository.findAll()
                .stream()
                .map(e -> MappingUtils.conversion(e, CreatedModelResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String createModel(CreateModelRequest param) {
        AlgorithmsDB checkAlg = algorithmsRepository.findByName(param.getNameAlg());
        if (checkAlg == null) {
            throw new DataProccessingToolsNotFound(param.getNameAlg());
        }
        checkParamValues.checkValue(param.getNameAlg(), param.getHyperparams());
        var modelImpl = modelsRepository.findByName(param.getNameModel());
        if (modelImpl == null) {
            modelImpl = ModelsDB.builder()
                    .name(param.getNameModel())
                    .status(EnumLabels.SENT_FOR_CREATE)
                    .algorithm(param.getNameAlg())
                    .hyperparams(String.valueOf(param.getHyperparams().stream().map(params -> params.getName() + ": " + params.getValue()).collect(Collectors.toList())))
                    .build();
        } else {
            throw new DuplicateNameError(param.getNameModel());
        }
        modelsRepository.save(modelImpl);
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(param.getNameAlg()));
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(GeneralObjectStructure.builder()
                        .name(param.getNameAlg())
                        .parameters(param.getHyperparams())
                        .build())
                .modelId(modelImpl.getId().toString())
                .modelLabel(EnumLabels.CREATE)
                .build());
        return EnumLabels.SENT_FOR_CREATE.getDescription();
    }

    @Transactional
    public String deleteModel(String modelName) {
        var model = modelsRepository.findByName(modelName);
        modelsRepository.deleteById(model.getId());
        return "Model " + modelName + " was deleted";
    }

    @Transactional
    public String trainModel(TrainPredictRequest param) {
        var modelImpl = modelsRepository.findByName(param.getModelName());
        if (modelImpl == null) {
            throw new ModelNotFoundException(param.getModelName());
        }
        modelImpl.setPreproccesingData(mappingUtils.PreproccesingDataToEntity(param, "TRAIN"));
        modelImpl.setStatus(EnumLabels.SENT_FOR_TRAIN);
        modelsRepository.save(modelImpl);
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(modelImpl.getAlgorithm()));
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(GeneralObjectStructure.builder()
                        .serializedData(modelImpl.getModel())
                        .build())
                .modelId(modelImpl.getId().toString())
                .features(param.getFeatures())
                .featuresHeader(param.getFeaturesHeader())
                .scalers(param.getScalers())
                .encoderLabels(param.getEncoderLabels())
                .encodersFeatures(param.getEncoderFeatures())
                .labels(param.getLabels())
                .modelLabel(EnumLabels.TRAIN)
                .build());
        return EnumLabels.SENT_FOR_TRAIN.getDescription();
    }

    @Transactional
    public String predictModel(TrainPredictRequest param) {
        var modelImpl = modelsRepository.findByName(param.getModelName());
        if (modelImpl == null) {
            throw new ModelNotFoundException(param.getModelName());
        }
        modelImpl.setPreproccesingData(mappingUtils.PreproccesingDataToEntity(param, "PREDICT"));
        var topic = providerRepository.findTopic(algorithmsRepository.findIdByAlgName(modelImpl.getAlgorithm()));
        streamBridge.send(topic, GeneralRequestResponse.builder()
                .model(GeneralObjectStructure.builder()
                        .serializedData(modelImpl.getModel())
                        .build())
                .modelId(modelImpl.getId().toString())
                .scalers(param.getScalers())
                .encodersFeatures(param.getEncoderFeatures())
                .encoderLabels(param.getEncoderLabels())
                .features(param.getFeatures())
                .modelLabel(EnumLabels.PREDICT)
                .build());
        return EnumLabels.SENT_FOR_PREDICT.getDescription();
    }
}
