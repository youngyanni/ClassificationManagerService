package ru.mtuci.ib.ml_service.classification_service.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.RequestForCreate;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.RequestForPredict;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.RequestForTrain;
import ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers.ResponseForSave;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.CreateModelRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.PredictionRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.TrainRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.*;
import ru.mtuci.ib.ml_service.classification_service.Model.AlgorithmsDB;
import ru.mtuci.ib.ml_service.classification_service.Model.ClassificationDB;
import ru.mtuci.ib.ml_service.classification_service.Model.ProviderDB;
import ru.mtuci.ib.ml_service.classification_service.repositories.AlgorithmsRepository;
import ru.mtuci.ib.ml_service.classification_service.repositories.ClassificationRepository;
import ru.mtuci.ib.ml_service.classification_service.repositories.ProviderRepository;
import org.springframework.cloud.stream.function.StreamBridge;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@Component
public class Classification_service {
    private final ClassificationRepository classificationRepository;
    private final ProviderRepository providerRepository;
    private final AlgorithmsRepository algorithmsRepository;

    private final StreamBridge streamBridge;
    private String modelName;
    private String currentProvider;

    /*Provider Registration*/
    @Bean
    public Consumer<ProviderRegistration> regProvider() {
        return info -> {
            ProviderDB providerInfo = ProviderDB.builder()
                    .name(info.getName())
                    .topic(info.getTopic())
                    .build();
            providerRepository.save(providerInfo);
            for(String algName:info.getAlgorithms()){
                algorithmsRepository.save(AlgorithmsDB.builder()
                        .algName(algName)
                        .providerDB(providerInfo)
                        .build());
            }
        };
    }

    //Список доступных алгоритмов
    public List<String> availableAlgorithms() {
        var testList = new ArrayList<>(providerRepository.findAll());
        var avaibleAlgs = new ArrayList<>();
        for (ProviderDB entry : testList) {
            List<String> algList = List.of(entry.getAlg()
                    .toString(1, entry.getAlg().length() - 1)
                    .replace(" ", "")
                    .split(","));
            for (String alg : algList) {
                avaibleAlgs.add(entry.getName() + "." + alg);
            }
        }
        return null;
    }

    //Список созданных моделей
    public List<CreatedModelResponse> getCreatedModels() {
        return classificationRepository.findAll()
                .stream()
                .map(entry -> CreatedModelResponse.builder()
                        .name(entry.getName())
                        .getMetrics(List.of(entry.getMetrics()))
                        .build())
                .collect(Collectors.toList());
    }

    //Create new model
    public String createModel(CreateModelRequest param) {
        String algName = param.getNameAlg()
                .substring(param.getNameAlg().indexOf(".") + 1, param.getNameAlg().length());
        modelName = param.getNameModel();
        String topic = "";
        List<ProviderDB> providerList = new ArrayList<>(providerRepository.findAll());
        for (ProviderDB entry : providerList) {
            List<String> algList = List.of(entry.getAlg()
                    .substring(1, entry.getAlg().length() - 1)
                    .replace(" ", "")
                    .split(","));
            for (String alg : algList) {
                if (alg.contains(algName)) {
                    currentProvider = entry.getName();
                    topic = entry.getTopic();
                    break;
                }
            }
        }
        streamBridge.send(topic, RequestForCreate.builder()
                .hyperParameters(param.getHyperParam())
                .tag("create").build());
        return param.getNameModel();
    }

    @Bean
    public Consumer<ResponseForSave> saveModelInDB() {
        return model -> {
            Blob state;
            try {
                state = new SerialBlob(model.getModel());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            classificationRepository.save(ClassificationDB.builder()
                    .name(modelName)
                    .model(state)
                    .status(model.getTag())
                    .provider(currentProvider)
                    .build());
        };
    }

    //Train model
    public TrainResponse trainModel(TrainRequest param) throws Exception {
        final var modelImpl = classificationRepository.findByName(param.getNameModel());
        if (modelImpl == null) {
            throw new Exception("Model not found");
        }
        modelName = modelImpl.getName();
        String topic = providerRepository.getTopic(modelImpl.getProvider());
        RequestForTrain test = RequestForTrain.builder()
                .matrixAttrTrain(param.getMatrixAttrTrain())
                .matrixAttrTest(param.getMatrixAttrTest())
                .model(modelImpl.getModel().getBytes(1L, (int) modelImpl.getModel().length()))
                .labels(param.getLabels())
                .tag("train").build();
        streamBridge.send(topic, test);
        return new TrainResponse();
    }
    //Как из Consumer вернуть значение в метод  create для тела ответа
    @Bean
    public Consumer<ResponseForSave> saveNewState() {
        return newStateModel -> {
            final var currentModel = classificationRepository.findByName(modelName);
            System.out.println(currentModel);
            Blob state;
            try {
                state = new SerialBlob(newStateModel.getModel());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            currentModel.setModel(state);
            currentModel.setStatus(newStateModel.getTag());
            classificationRepository.save(currentModel);
        };
    }

    //Predict model
    //HEX вместо BLOB
    public PredictionResponse predictModel(PredictionRequest param) throws Exception {
        var modelImpl = classificationRepository.findByName(param.getNameModel());
        if (modelImpl == null) {
            throw new Exception("Model not found");
        }
        modelName = modelImpl.getName();
        String topic = providerRepository.getTopic(modelImpl.getProvider());
        streamBridge.send(topic, RequestForPredict.builder()
                .model(modelImpl.getModel().getBytes(1L, (int) modelImpl.getModel().length()))
                .matrixAttr(param.getMatrixAttr())
                .tag("predict")
                .build());
        return null;
    }

    @Bean
    public Consumer<PredictionResponse> savePredictModel() {
        return predModel -> {
            final var currentModel = classificationRepository.findByName(modelName);
            currentModel.setStatus(predModel.getTag());
            classificationRepository.save(currentModel);
        };
    }
}
