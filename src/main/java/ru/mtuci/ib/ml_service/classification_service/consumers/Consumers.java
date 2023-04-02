package ru.mtuci.ib.ml_service.classification_service.consumers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.GeneralRequestResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.ProviderRegistration;
import ru.mtuci.ib.ml_service.classification_service.model.AlgorithmsDB;
import ru.mtuci.ib.ml_service.classification_service.model.HyperparametersDB;
import ru.mtuci.ib.ml_service.classification_service.model.ProviderDB;
import ru.mtuci.ib.ml_service.classification_service.enums.EnumLabels;
import ru.mtuci.ib.ml_service.classification_service.repositories.ModelsRepository;
import ru.mtuci.ib.ml_service.classification_service.repositories.ProviderRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Consumers {
    private final ProviderRepository providerRepository;
    private final ModelsRepository modelsRepository;

    //Provider reg
    @Bean
    public Consumer<ProviderRegistration> regProvider() {
        return info -> {
            ProviderDB provider = ProviderDB.builder()
                    .name(info.getName())
                    .topic(info.getTopic())
                    .alg(info.getAlgorithms()
                            .stream()
                            .map(algs -> AlgorithmsDB.builder()
                                    .algName(algs.getAlgName())
                                    .hyperparametersDBList(algs.getHyperparameters()
                                            .stream()
                                            .map(hyperparam -> HyperparametersDB.builder()
                                                    .flag(hyperparam.getDescriptionFlag())
                                                    .description(hyperparam.getDescription())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
            providerRepository.save(provider);
        };
    }

    @Bean
    public Consumer<GeneralRequestResponse> saveModelInDB() {
        return model -> {
            Blob state;
            try {
                state = new SerialBlob(model.getModel().getCreatedModel().getBytes());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            var modelImpl = modelsRepository.findModelByName(model.getModelId());
            modelImpl.setModel(state);
            modelImpl.setStatus(model.getModelLabel().getDescript());
            modelsRepository.save(modelImpl);
        };
    }

    @Bean
    public Consumer<GeneralRequestResponse> saveNewStateInDB() {
        return newStateModel -> {
            final var currentModel = modelsRepository.findByName(newStateModel.getModelId());
            Blob state;
            try {
                state = new SerialBlob(newStateModel.getModel().getCreatedModel().getBytes());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            currentModel.setModel(state);
            currentModel.setMetrics(newStateModel.getMetrics());
            currentModel.setStatus(newStateModel.getModelLabel().getDescript());
            modelsRepository.save(currentModel);
        };
    }

    @Bean
    public Consumer<GeneralRequestResponse> savePredictModel() {
        return predModel -> {
            final var currentModel = modelsRepository.findByName(predModel.getModelId());
            currentModel.setStatus(predModel.getModelLabel().getDescript());
            currentModel.setPredict(Arrays.toString(predModel.getPrediction()));
            modelsRepository.save(currentModel);
        };
    }
}
