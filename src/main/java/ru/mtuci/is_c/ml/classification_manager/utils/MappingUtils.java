package ru.mtuci.is_c.ml.classification_manager.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.GeneralDTO;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.GeneralRequestResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.requests.TrainPredictRequest;
import ru.mtuci.is_c.ml.classification_manager.dto.responses.AvailableDataProccesingToolsEntityToDTOResponse;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.ParameterDTO;
import ru.mtuci.is_c.ml.classification_manager.model.AlgorithmsDB;
import ru.mtuci.is_c.ml.classification_manager.model.DictDB;
import ru.mtuci.is_c.ml.classification_manager.model.EncodersDB;
import ru.mtuci.is_c.ml.classification_manager.model.EnumDB;
import ru.mtuci.is_c.ml.classification_manager.model.HyperparametersDB;
import ru.mtuci.is_c.ml.classification_manager.model.IntFloatDB;
import ru.mtuci.is_c.ml.classification_manager.model.PreproccesingDataDB;
import ru.mtuci.is_c.ml.classification_manager.model.ProvidersDB;
import ru.mtuci.is_c.ml.classification_manager.model.ScalersDB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MappingUtils {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final CheckParamValues checkParamValues;

    public static <T> T conversion(Object hyperparameterDTOInfo, Class<T> classToConvert) {
        return modelMapper.map(hyperparameterDTOInfo, classToConvert);
    }

    public static HyperparametersDB rangeValues(ParameterDTO values) {
        var entity = modelMapper.map(values, HyperparametersDB.class);
        switch (values.getType().getName()) {
            case "int", "float" -> entity.setIntFloatRange(conversion(values.getType(), IntFloatDB.class));
            case "dict" -> entity.setDictRange(conversion(values.getType(), DictDB.class));
            case "enum" -> entity.setEnumRange(values.getType()
                    .getEnumValues()
                    .stream()
                    .map(e -> EnumDB.builder().enumValues(e.toString()).build())
                    .collect(Collectors.toList()));
        }
        return entity;
    }

    public static AlgorithmsDB mapAlgorithmsToEntity(GeneralDTO algInfo) {
        var algorithmsEntity = modelMapper.map(algInfo, AlgorithmsDB.class);
        algorithmsEntity.setName(algInfo.getName());
        algorithmsEntity.setHyperparametersDBList(algInfo.getParameters()
                .stream()
                .map(MappingUtils::rangeValues)
                .collect(Collectors.toList()));
        return algorithmsEntity;
    }

    public static EncodersDB mapEncodersToEntity(GeneralDTO encoderInfo) {
        var encoderEntity = modelMapper.map(encoderInfo, EncodersDB.class);
        encoderEntity.setEncoderName(encoderInfo.getName());
        encoderEntity.setHyperparametersDBList(encoderInfo.getParameters()
                .stream()
                .map(MappingUtils::rangeValues)
                .collect(Collectors.toList()));
        return encoderEntity;
    }

    public static ScalersDB mapScalersToEntity(GeneralDTO scalersInfo) {
        var ScalerEntity = modelMapper.map(scalersInfo, ScalersDB.class);
        ScalerEntity.setScalerName(scalersInfo.getName());
        ScalerEntity.setHyperparametersDBList(scalersInfo.getParameters()
                .stream()
                .map(MappingUtils::rangeValues)
                .collect(Collectors.toList()));
        return ScalerEntity;
    }

    public static GeneralDTO EntityToDTO(Object entity) {
        var entityToDTOResponse = new GeneralDTO();
        if (entity instanceof AlgorithmsDB algorithmsDB) {
            entityToDTOResponse.setName(algorithmsDB.getName());
            entityToDTOResponse.setParameters(algorithmsDB.getHyperparametersDBList()
                    .stream()
                    .map(e -> conversion(e, ParameterDTO.class))
                    .collect(Collectors.toList()));
        } else if (entity instanceof EncodersDB encoderDB) {
            entityToDTOResponse.setName(encoderDB.getEncoderName());
            entityToDTOResponse.setParameters(encoderDB.getHyperparametersDBList()
                    .stream()
                    .map(e -> conversion(e, ParameterDTO.class))
                    .collect(Collectors.toList()));
        } else if (entity instanceof ScalersDB scalerDB) {
            entityToDTOResponse.setName(scalerDB.getScalerName());
            entityToDTOResponse.setParameters(scalerDB.getHyperparametersDBList()
                    .stream()
                    .map(e -> conversion(e, ParameterDTO.class))
                    .collect(Collectors.toList()));
        }
        return entityToDTOResponse;
    }

    public static AvailableDataProccesingToolsEntityToDTOResponse DataProccesingToolsEntityToDTO(ProvidersDB alginfo) {
        var availableDataProccesingTools = modelMapper.map(alginfo, AvailableDataProccesingToolsEntityToDTOResponse.class);
        availableDataProccesingTools.setProviderName(alginfo.getProviderName());
        availableDataProccesingTools.setAlgList(alginfo.getAlg()
                .stream()
                .map(MappingUtils::EntityToDTO)
                .collect(Collectors.toList()));
        availableDataProccesingTools.setEncoderList(alginfo.getEndcoder()
                .stream()
                .map(MappingUtils::EntityToDTO)
                .collect(Collectors.toList()));
        availableDataProccesingTools.setScalersList(alginfo.getScaler()
                .stream()
                .map(MappingUtils::EntityToDTO)
                .collect(Collectors.toList()));
        return availableDataProccesingTools;
    }

    public List<PreproccesingDataDB> PreproccesingDataToEntity(TrainPredictRequest param, String stage) {
        List<PreproccesingDataDB> preproccesingDataTools = new ArrayList<PreproccesingDataDB>();
        if (param.getEncoderFeatures() != null) {
            param.getEncoderFeatures()
                    .forEach(encodersFeature -> {
                        var encoderFeature = PreproccesingDataDB.builder()
                                .name(encodersFeature.getEncoder().getName())
                                .featureName(encodersFeature.getFeatureName())
                                .type("EncoderFeature")
                                .stage(stage)
                                .build();
                        if (encodersFeature.getEncoder().getParameters() != null) {
                            checkParamValues.checkValue(encodersFeature.getEncoder().getName(), encodersFeature.getEncoder().getParameters());
                            encoderFeature.setParameters(String.valueOf(encodersFeature.getEncoder().getParameters().stream().map(params -> params.getName() + ": " + params.getValue()).collect(Collectors.toList())));
                        }
                        preproccesingDataTools.add(encoderFeature);
                    });
        }
        if (param.getEncoderLabels() != null) {
            PreproccesingDataDB encoderLabels = PreproccesingDataDB.builder()
                    .type("EncoderLabels")
                    .name(param.getEncoderLabels().getName())
                    .stage(stage)
                    .build();
            if (param.getEncoderLabels().getParameters() != null) {
                checkParamValues.checkValue(param.getEncoderLabels().getName(), param.getEncoderLabels().getParameters());
                encoderLabels.setParameters(String.valueOf(param.getEncoderLabels().getParameters().stream().map(params -> params.getName() + ": " + params.getValue()).collect(Collectors.toList())));
            }
            preproccesingDataTools.add(encoderLabels);
        }
        if (param.getScalers() != null) {
            param.getScalers()
                    .forEach(scalers -> {
                        PreproccesingDataDB scaler = PreproccesingDataDB.builder()
                                .type("Scaler")
                                .name(scalers.getName())
                                .stage(stage)
                                .build();
                        if (scalers.getParameters() != null) {
                            checkParamValues.checkValue(scalers.getName(), scalers.getParameters());
                            scaler.setParameters(String.valueOf(param.getScalers().stream().map(params -> params.getParameters().stream().map(parameters -> parameters.getName() + ": " + parameters.getValue()).collect(Collectors.toList()))));
                        }
                        preproccesingDataTools.add(scaler);
                    });
        }
        return preproccesingDataTools;
    }

    public List<PreproccesingDataDB> savePreproccesingData(GeneralRequestResponse modelParameters, List<PreproccesingDataDB> preproccesingData) {
        if (modelParameters.getEncoderLabels().getSerializedData().length != 0) {
            preproccesingData.forEach(data -> {
                if (data.getType().equals("EncoderLabels")) {
                    data.setData(modelParameters.getEncoderLabels().getSerializedData());
                }
            });
        }
        modelParameters.getScalers()
                .forEach(scaler -> {
                    if (scaler.getSerializedData() != null) {
                        preproccesingData.forEach(data -> {
                            if (data.getName().equals(scaler.getName())) {
                                data.setData(scaler.getSerializedData());
                            }
                        });
                    }
                });
        modelParameters.getEncodersFeatures()
                .forEach(encoderFeatures -> {
                    if (encoderFeatures.getEncoder().getSerializedData().length != 0) {
                        preproccesingData.forEach(data -> {
                            if (data.getFeatureName() != null && data.getType().equals("EncoderFeature")) {
                                if ((data.getName().equals(encoderFeatures.getEncoder().getName())) && ((data.getFeatureName().equals(encoderFeatures.getFeatureName())))) {
                                    data.setData(encoderFeatures.getEncoder().getSerializedData());
                                }
                            }
                        });
                    }
                });
        return preproccesingData;
    }
}