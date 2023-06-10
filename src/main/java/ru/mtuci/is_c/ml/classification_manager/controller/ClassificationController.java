package ru.mtuci.is_c.ml.classification_manager.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.CreateModelRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.TrainPredictRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.AvailableDataProccesingToolsEntityToDTOResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.CreatedModelResponse;
import ru.mtuci.is_c.ml.classification_manager.service.ClassificationManagerService;

import java.util.List;


@RestController
@RequestMapping("/model")
@RequiredArgsConstructor
public class ClassificationController {
    private final ClassificationManagerService service;

    @GetMapping(value = "/getInfo")
    public List<AvailableDataProccesingToolsEntityToDTOResponse> getDataProcessingToolsInfo() {
        return service.dataProcessingToolsInfo();
    }
    @GetMapping(value = "/createdModel")
    public List<CreatedModelResponse> createdModels() {
        return service.getCreatedModels();
    }

    @PostMapping(value = "/create")
    public String createModel(@RequestBody CreateModelRequest Param) {
        return service.createModel(Param);
    }

    @PostMapping(value = "/train")
    public String trainModel(@RequestBody TrainPredictRequest Param) {
        return service.trainModel(Param);
    }

    @PostMapping(value = "/predict")
    public String predictModel(@RequestBody TrainPredictRequest Param) {
        return service.predictModel(Param);
    }

    @DeleteMapping(value = "/deleteModel/{modelName}")
    public String deleteModel(@PathVariable(value = "modelName") String modelName) {
        return service.deleteModel(modelName);
    }
}
