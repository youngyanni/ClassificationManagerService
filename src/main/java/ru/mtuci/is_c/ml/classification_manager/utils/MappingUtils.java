package ru.mtuci.is_c.ml.classification_manager.utils;

import org.modelmapper.ModelMapper;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.AlgorithmsDTO;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.AvailableAlgorithmsResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.HyperparameterDTO;
import ru.mtuci.is_c.ml.classification_manager.model.AlgorithmsDB;
import ru.mtuci.is_c.ml.classification_manager.model.HyperparametersDB;
import ru.mtuci.is_c.ml.classification_manager.model.ProviderDB;

import java.util.stream.Collectors;

public class MappingUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T> T conversion(Object hyperparameterDTOInfo, Class<T> classToConvert) {
        return modelMapper.map(hyperparameterDTOInfo, classToConvert);
    }

    public static AlgorithmsDB mapAlgorithmsToEntity(AlgorithmsDTO algInfo) {
        var AlgorithmsEntity = modelMapper.map(algInfo, AlgorithmsDB.class);
        AlgorithmsEntity.setAlgName(algInfo.getAlgorithmName());
        AlgorithmsEntity.setHyperparametersDBList(algInfo.getHyperparameters()
                .stream()
                .map(e -> conversion(e, HyperparametersDB.class))
                .collect(Collectors.toList()));
        return AlgorithmsEntity;
    }

    public static AlgorithmsDTO AlgorithmsEntityToDTO(AlgorithmsDB algInfo) {
        var algorithm = modelMapper.map(algInfo, AlgorithmsDTO.class);
        algorithm.setAlgorithmName(algInfo.getAlgName());
        algorithm.setHyperparameters(algInfo.getHyperparametersDBList()
                .stream()
                .map(e -> conversion(e, HyperparameterDTO.class))
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