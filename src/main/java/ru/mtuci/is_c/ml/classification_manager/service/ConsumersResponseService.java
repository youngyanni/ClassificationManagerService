package ru.mtuci.is_c.ml.classification_manager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.ErrorMessage;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.ProviderRegistrationRequest;
import ru.mtuci.is_c.ml.classification_manager.enums.EnumLabels;
import ru.mtuci.is_c.ml.classification_manager.model.ProvidersDB;
import ru.mtuci.is_c.ml.classification_manager.repositories.ModelsRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.PreproccesingDataRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.ProviderRepository;
import ru.mtuci.is_c.ml.classification_manager.utils.MappingUtils;

import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ConsumersResponseService {
    private final ModelsRepository modelsRepository;
    private final ProviderRepository providerRepository;
    private final MappingUtils mappingUtils;

    @Transactional
    public void providerRegistration(ProviderRegistrationRequest modelParameters) {
        providerRepository.save(ProvidersDB.builder()
                .providerName(modelParameters.getProvider())
                .topic(modelParameters.getTopic())
                .alg(modelParameters.getAlgorithms()
                        .stream()
                        .map(MappingUtils::mapAlgorithmsToEntity)
                        .collect(Collectors.toList()))
                .endcoder(modelParameters.getEncoders()
                        .stream()
                        .map(MappingUtils::mapEncodersToEntity)
                        .collect(Collectors.toList()))
                .scaler(modelParameters.getScalers()
                        .stream()
                        .map(MappingUtils::mapScalersToEntity)
                        .collect(Collectors.toList()))
                .build());

    }

    @Transactional
    public void saveCreatedModel(GeneralRequestResponse modelParameters) {
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setModel(modelParameters.getModel().getSerializedData());
        modelImpl.setStatus(EnumLabels.CREATED);
        modelImpl.setErrorMessage(null);
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void saveTrainedModel(GeneralRequestResponse modelParameters) {
        var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setPreproccesingData(mappingUtils.savePreproccesingData(modelParameters, modelImpl.getPreproccesingData()));
        modelImpl.setModel(modelParameters.getModel().getSerializedData());
        modelImpl.setStatus(EnumLabels.TRAINED);
        modelImpl.setErrorMessage(null);
        modelsRepository.save(modelImpl);
    }

    @Transactional
    public void savePredictedModel(GeneralRequestResponse modelParameters) {
        final var modelImpl = modelsRepository.findById(UUID.fromString(modelParameters.getModelId())).orElseThrow();
        modelImpl.setStatus(EnumLabels.PREDICTED);
        modelImpl.setErrorMessage(null);
        modelImpl.setPredictLabel(modelParameters.getLabels().toString());
        modelImpl.setDistributions(modelImpl.getDistributions());
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
