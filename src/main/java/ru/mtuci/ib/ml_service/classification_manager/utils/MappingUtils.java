package ru.mtuci.ib.ml_service.classification_manager.utils;


import org.modelmapper.ModelMapper;
import ru.mtuci.ib.ml_service.classification_manager.dto.algorithms.AlgorithmsDTO;
import ru.mtuci.ib.ml_service.classification_manager.dto.responses.AvailableAlgorithmsResponse;
import ru.mtuci.ib.ml_service.classification_manager.dto.algorithms.HyperparameterDTO;
import ru.mtuci.ib.ml_service.classification_manager.model.AlgorithmsDB;
import ru.mtuci.ib.ml_service.classification_manager.model.HyperparametersDB;
import ru.mtuci.ib.ml_service.classification_manager.model.ProviderDB;

import java.util.stream.Collectors;

public class MappingUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static HyperparametersDB hyperparametersEtnity(HyperparameterDTO hyperparameterDTOInfo) {
        return modelMapper.map(hyperparameterDTOInfo, HyperparametersDB.class);
    }

    public static AlgorithmsDB mapAlgorithmsToEntity(AlgorithmsDTO algInfo) {
        var AlgorithmsEntity = modelMapper.map(algInfo, AlgorithmsDB.class);
        AlgorithmsEntity.setHyperparametersDBList(algInfo.getHyperparameterRespons()
                .stream()
                .map(MappingUtils::hyperparametersEtnity)
                .collect(Collectors.toList()));
        return AlgorithmsEntity;
    }

    public static HyperparameterDTO hyperparameterEntityToDTO(HyperparametersDB hyperparameters) {
        return modelMapper.map(hyperparameters, HyperparameterDTO.class);
    }

    public static AlgorithmsDTO AlgorithmsEntityToDTO(AlgorithmsDB algInfo) {
        var algorithm = modelMapper.map(algInfo, AlgorithmsDTO.class);
        algorithm.setHyperparameterRespons(algInfo.getHyperparametersDBList()
                .stream()
                .map(MappingUtils::hyperparameterEntityToDTO)
                .collect(Collectors.toList()));
        return algorithm;
    }

    public static AvailableAlgorithmsResponse AlgorithmsEntityToDTO(ProviderDB alginfo) {
        var algorithmsResponse = modelMapper.map(alginfo, AvailableAlgorithmsResponse.class);
        algorithmsResponse.setPoroviderName(alginfo.getProviderName());
        algorithmsResponse.setAlgList(alginfo.getAlg()
                .stream()
                .map(MappingUtils::AlgorithmsEntityToDTO)
                .collect(Collectors.toList()));
        return algorithmsResponse;
    }
}