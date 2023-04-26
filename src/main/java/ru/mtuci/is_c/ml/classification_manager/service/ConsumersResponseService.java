package ru.mtuci.is_c.ml.classification_manager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
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
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ConsumersResponseService {
    private final ModelsRepository modelsRepository;
    private final ProviderRepository providerRepository;

    @Transactional
    public void providerRegistration(ProviderRegistrationRequest modelParameters) {
        System.out.println(modelParameters);
        var provider = ProviderDB.builder()
                .providerName(modelParameters.getProvider())
                .topic(modelParameters.getTopic())
                .alg(modelParameters.getAlgorithms()
                        .stream()
                        .map(MappingUtils::mapAlgorithmsToEntity)
                        .collect(Collectors.toList()))
                .build();
        providerRepository.save(provider);
    }

    @Transactional
    public void saveCreatedModel(GeneralRequestResponse modelParameters) {
        System.out.println("Создание модели");
        Blob state;
        try {
            state = new SerialBlob(Base64.decodeBase64(modelParameters.getSerializedModelData().getModel()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setModel(state);
        modelImpl.setStatus(EnumLabels.CREATED);
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void saveTrainedModel(GeneralRequestResponse modelParameters) {
        System.out.println("Обучение");
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        Blob state;
        try {
            state = new SerialBlob(Base64.decodeBase64(modelParameters.getSerializedModelData().getModel()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modelImpl.setAttribute(modelParameters.getSerializedModelData().getAttribute());
        modelImpl.setModel(state);
        modelImpl.setMetrics(modelParameters.getMetrics());
        modelImpl.setStatus(EnumLabels.TRAINED);
        modelsRepository.saveAndFlush(modelImpl);
    }

    @Transactional
    public void savePredictedModel(GeneralRequestResponse modelParameters) {
        final var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setStatus(EnumLabels.PREDICTED);
        modelImpl.setPredict("" + modelParameters.getSerializedModelData().getDistributions());
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void errorHandler(ErrorMessage errorInfo) {
        final var modelImpl = modelsRepository.findById(UUID.fromString(errorInfo.getModelId())).orElseThrow();
        var errorMessageBuilder = "" + errorInfo.getErrorType() +
                "; " + errorInfo.getErrorMessage() +
                "; " + errorInfo.getLocalDateTime();
        modelImpl.setErrorMessage(errorMessageBuilder);
        modelsRepository.save(modelImpl);
    }
}
