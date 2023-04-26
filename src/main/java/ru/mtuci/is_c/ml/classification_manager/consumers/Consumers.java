package ru.mtuci.is_c.ml.classification_manager.consumers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.ErrorMessage;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.ProviderRegistrationRequest;
import ru.mtuci.is_c.ml.classification_manager.service.ConsumersResponseService;
import ru.mtuci.is_c.ml.classification_manager.utils.MappingUtils;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class Consumers {
    private final ConsumersResponseService consumersResponse;
    @Bean
    public Consumer<ProviderRegistrationRequest> registrationRequest(){
        return consumersResponse::providerRegistration;
    }

    @Bean
    public Consumer<GeneralRequestResponse> saveModel(){
        return model -> {
            switch (model.getModelLabel()){
                case CREATE -> consumersResponse.saveCreatedModel(model);
                case TRAIN -> consumersResponse.saveTrainedModel(model);
                case PREDICT -> consumersResponse.savePredictedModel(model);
            }
        };
    }
    @Bean
    public Consumer<ErrorMessage> errorHandle() {
        return consumersResponse::errorHandler;
    }

   /* @Bean
    public Consumer<GeneralRequestResponse> saveCreatedModel() {
        return consumersResponse::saveCreatedModel;
    }*/

    /*@Bean
    public Consumer<GeneralRequestResponse> saveTrainedModel() {
        return consumersResponse::saveTrainedModel;
    }*/

    /*@Bean
    public Consumer<GeneralRequestResponse> savePredictModel() {
        return consumersResponse::savePredictedModel;
    }*/
}
