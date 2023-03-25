package ru.mtuci.ib.ml_service.classification_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.CreateModelRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.PredictionRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Request.TrainRequest;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.CreatedModelResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.PredictionResponse;
import ru.mtuci.ib.ml_service.classification_service.DTO_Response.TrainResponse;
import ru.mtuci.ib.ml_service.classification_service.Service.Classification_service;

import java.util.List;


@RestController
@RequestMapping("/model")
@RequiredArgsConstructor
public class ClassificationController {
    private final Classification_service service;

    @GetMapping(value = "/algorithms")
    public List<String> getAvailableAlgorithms() {
        return service.availableAlgorithms();
    }

    @GetMapping(value = "/createdModel")
    public List<CreatedModelResponse> createdModels() {
        return service.getCreatedModels();
    }

   @PostMapping(value = "/create")
    public String createModel(@RequestBody CreateModelRequest Param){
        return service.createModel(Param);
    }

    @PostMapping(value = "/train")
    public ResponseEntity<String> trainModel(@RequestBody TrainRequest Param) throws Exception {
        try {
            TrainResponse response = service.trainModel(Param);
            return ResponseEntity.ok(String.valueOf(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/predict")
    public ResponseEntity<String> predictModel(@RequestBody PredictionRequest Param) throws Exception {
        try {
            PredictionResponse response = service.predictModel(Param);
            return ResponseEntity.ok(String.valueOf(response));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
