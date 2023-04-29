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
        providerRepository.save(ProviderDB.builder()
                .providerName(modelParameters.getProvider())
                .topic(modelParameters.getTopic())
                .alg(modelParameters.getAlgorithms()
                        .stream()
                        .map(MappingUtils::mapAlgorithmsToEntity)
                        .collect(Collectors.toList()))
                .build());
    }

    @Transactional
    public void saveCreatedModel(GeneralRequestResponse modelParameters) {
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setModel(modelParameters.getSerializedModelData().getModel());
        modelImpl.setStatus(EnumLabels.CREATED);
        modelImpl.setErrorMessage("");
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void saveTrainedModel(GeneralRequestResponse modelParameters) {
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setAttribute(modelParameters.getSerializedModelData().getAttribute());
        modelImpl.setModel(modelParameters.getSerializedModelData().getModel());
        modelImpl.setMetrics(Arrays.toString(modelParameters.getMetrics()));
        modelImpl.setStatus(EnumLabels.TRAINED);
        modelImpl.setErrorMessage("");
        modelsRepository.saveAndFlush(modelImpl);
    }

    @Transactional
    public void savePredictedModel(GeneralRequestResponse modelParameters) {
        final var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setStatus(EnumLabels.PREDICTED);
        modelImpl.setErrorMessage("");
        modelImpl.setPredict(String.valueOf(modelParameters.getSerializedModelData().getLabels()) + modelParameters.getSerializedModelData().getDistributions());
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
