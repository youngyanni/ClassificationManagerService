package ru.mtuci.is_c.ml.classification_manager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.ErrorMessage;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.ProviderRegistrationRequest;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;
import ru.mtuci.is_c.ml.classification_manager.model.ProviderDB;
import ru.mtuci.is_c.ml.classification_manager.repositories.ModelsRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.ProviderRepository;
import ru.mtuci.is_c.ml.classification_manager.utils.MappingUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ConsumersResponseService {
    private final ModelsRepository modelsRepository;
    private final ProviderRepository providerRepository;

    @Transactional
    public void providerRegistration(ProviderRegistrationRequest modelParameters) {
        var provider = ProviderDB.builder()
                .providerName(modelParameters.getProviderName())
                .topic(modelParameters.getTopicName())
                .alg(modelParameters.getAlgorithms()
                        .stream()
                        .map(MappingUtils::mapAlgorithmsToEntity)
                        .collect(Collectors.toList()))
                .build();
        providerRepository.save(provider);
    }

    @Transactional
    public void saveCreatedModel(GeneralRequestResponse modelParameters) {
        Blob state;
        try {
            state = new SerialBlob(modelParameters.getModel().getCreatedModel().getBytes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setModel(state);
        modelImpl.setStatus(modelParameters.getModelLabel());
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void saveTrainedModel(GeneralRequestResponse modelParameters) {
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        Blob state;
        try {
            state = new SerialBlob(modelParameters.getModel().getCreatedModel().getBytes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modelImpl.setModel(state);
        modelImpl.setMetrics(modelParameters.getMetrics());
        modelImpl.setStatus(modelParameters.getModelLabel());
        modelsRepository.saveAndFlush(modelImpl);
    }

    @Transactional
    public void savePredictedModel(GeneralRequestResponse modelParameters) {
        final var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setStatus(modelParameters.getModelLabel());
        modelImpl.setPredict(Arrays.toString(modelParameters.getPrediction()));
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void errorHandler(ErrorMessage errorInfo) {
        final var modelImpl = modelsRepository.findById(UUID.fromString(errorInfo.getModelId())).orElseThrow();
        modelImpl.setErrorType(errorInfo.getErrorType());
        modelImpl.setErrorMessage(errorInfo.getErrorMessage());
        modelImpl.setDataTime(errorInfo.getLocalDateTime());
        modelImpl.setStatus(errorInfo.getStage());
        modelsRepository.save(modelImpl);
    }
}
