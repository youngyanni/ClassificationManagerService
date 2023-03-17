package ru.mtuci.ib.ml_service.classification_service.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.CreateModelRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.PredictionRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.TrainRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.CreatedModelResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.PredictionResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.TrainResponse;
import ru.mtuci.ib.ml_service.classification_service.Model.Classification_DB;
import ru.mtuci.ib.ml_service.classification_service.Provider.InterfaceProvider;
import ru.mtuci.ib.ml_service.classification_service.Provider.PythonClassificationAlgorithmsProvider;
import ru.mtuci.ib.ml_service.classification_service.Provider.WekaClassificationAlgorithmsProvider;
import ru.mtuci.ib.ml_service.classification_service.repositories.ClassificationRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class Classification_service  {
    @Autowired
    private final ClassificationRepository classificationRepository;
    InterfaceProvider Provider;/*Не должно быть  */
    /*Мапа в которой храниться доступные провайдеры и их алгоритмы*/
    private final Map<String,List<String>> Algorithms = new HashMap<>();

    public List<String> availableAlgorithms(){
        List<String> avaibleAlg = new ArrayList<>();
        /*
        * Динамическое добавление провайдеров, т.к. в будущем их может быть больше
        * */
        Provider = new PythonClassificationAlgorithmsProvider();
        for(int i = 0; i< Provider.getAlg().size(); i++){
            Algorithms.put(Provider.getName(),Provider.getAlg());
        }
        Provider = new WekaClassificationAlgorithmsProvider();
        for(int i = 0; i< Provider.getAlg().size(); i++){
            Algorithms.put(Provider.getName(),Provider.getAlg());
        }
        //Добавление префикса в название классификатора
        for (List<String> values: Algorithms.values()){
            for(String value: values){
                avaibleAlg.add(providerName(value)+"."+value);
            }
        }
        return avaibleAlg;
    }
    //Список созданных моделей
    public List<CreatedModelResponse> createdModels(){
        return classificationRepository.getAllModels();
    }

    //Create new model
    public Integer createModel(CreateModelRequest param) throws SQLException {
        availableAlgorithms();
        String currentProvider = providerName(param.getNameAlg());
        /*В каком формате приходит созданная модель от сервиса?
        * Blob model = request to Provider(createModel(param);
        * */
        /*request to currentProvider*/
        if (currentProvider.contains("Weka")){
            Provider = new WekaClassificationAlgorithmsProvider();
        } else if (currentProvider.contains("Python")) {
            Provider = new PythonClassificationAlgorithmsProvider();
        }
        else return null;
        Blob blob = new SerialBlob(Provider.newModel(param.getHyperParam()));
        classificationRepository.save(Classification_DB.builder()
                .name(param.getNameAlg())
                .Model(blob)
                .nameAlg(param.getNameAlg().replace(currentProvider+".",""))
                .build());
        return classificationRepository.getIdByModel(new String(blob.getBytes(1L,(int) blob.length())));
    }
    //train model
    public TrainResponse trainModel(TrainRequest param)throws Exception{
        final var modelImpl = classificationRepository.findByName(param.getNameModel());
        if (modelImpl==null){
            throw new Exception("Model not found");
        }
        /*Название нужного провайдера*/
        String currentProvider = providerName(modelImpl.getNameAlg());
        /*
        Blob newState = "request to Provider trainModel(modelImpl.getModel())";
        Blob newstate = "ass".trainModel(modelImpl.getModel());
        */
        if (currentProvider.contains("Weka")){
            Provider = new WekaClassificationAlgorithmsProvider();
        } else if (currentProvider.contains("Python")) {
            Provider = new PythonClassificationAlgorithmsProvider();
        }
        Blob newState = Provider.trainModel(modelImpl.getModel());
        modelImpl.setModel(newState);
        //Вызов у провайдера алгоритма predict
        //Provider.predictModel();
        //Построение матрицы ошибок
        classificationRepository.save(modelImpl);
        return new TrainResponse();
    }
    //Predict model
    public PredictionResponse predictModel(PredictionRequest param) throws Exception {
        /*Запись из бд по имени модели*/
        final var modelImpl = classificationRepository.findByName(param.getNameModel());
        if (modelImpl==null){
            throw new Exception("Model not found");
        }
        /*Название нужного провайдера*/
        String currentProvider = providerName(modelImpl.getNameAlg());
        /**/
        if(currentProvider.contains("Weka")){
            Provider = new WekaClassificationAlgorithmsProvider();
            return Provider.predictModel(modelImpl.getModel(),param.getMatrixAttr());
        } else if (currentProvider.contains("Python")) {
            Provider = new PythonClassificationAlgorithmsProvider();
            return Provider.predictModel(modelImpl.getModel(),param.getMatrixAttr());
        }
        return null;
    }
    /*Метод для определения нужного правайдера по названию алгоритма*/
    private String providerName(String NameAlg){
        String nameOfProvider;
        nameOfProvider = Algorithms
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(NameAlg.substring(NameAlg.indexOf(".")+1,NameAlg.length())))
                .map(Map.Entry::getKey).collect(Collectors.joining(""));
        return nameOfProvider;
    }
}
