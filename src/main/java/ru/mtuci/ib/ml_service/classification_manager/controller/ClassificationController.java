package ru.mtuci.ib.ml_service.classification_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.ib.ml_service.classification_manager.dto.requests.CreateModelRequest;
import ru.mtuci.ib.ml_service.classification_manager.dto.requests.PredictionRequest;
import ru.mtuci.ib.ml_service.classification_manager.dto.requests.TrainRequest;
import ru.mtuci.ib.ml_service.classification_manager.dto.responses.AvailableAlgorithmsResponse;
import ru.mtuci.ib.ml_service.classification_manager.dto.responses.CreatedModelResponse;
import ru.mtuci.ib.ml_service.classification_manager.dto.responses.PredictionResponse;
import ru.mtuci.ib.ml_service.classification_manager.service.ClassificationManagerService;

import java.util.List;


@RestController
@RequestMapping("/model")
@RequiredArgsConstructor
public class ClassificationController {
    private final ClassificationManagerService service;

    @GetMapping(value = "/algorithms")
    public List<AvailableAlgorithmsResponse> getAvailableAlgorithms() {
        return service.availableAlgorithms();
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
    public String trainModel(@RequestBody TrainRequest Param) throws Exception {
        return service.trainModel(Param);
    }

    @PostMapping(value = "/predict")
    public PredictionResponse predictModel(@RequestBody PredictionRequest Param) throws Exception {
        return service.predictModel(Param);
    }

    //@GetMapping(value = "/getModelInfo")

}
