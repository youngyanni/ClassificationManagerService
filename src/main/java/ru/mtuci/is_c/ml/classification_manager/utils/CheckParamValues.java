package ru.mtuci.is_c.ml.classification_manager.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mtuci.is_c.ml.classification_manager.dto.providers.Parameters;
import ru.mtuci.is_c.ml.classification_manager.exception.ValueOutOfRange;
import ru.mtuci.is_c.ml.classification_manager.model.EnumDB;
import ru.mtuci.is_c.ml.classification_manager.model.HyperparametersDB;
import ru.mtuci.is_c.ml.classification_manager.model.IntFloatDB;
import ru.mtuci.is_c.ml.classification_manager.repositories.DictionaryRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.EnumRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.HyperparameteresRepository;
import ru.mtuci.is_c.ml.classification_manager.repositories.IntFloatRepository;

import java.util.List;



@RequiredArgsConstructor
@Component
public class CheckParamValues {
    private final HyperparameteresRepository hyperparameteresRepository;
    private final IntFloatRepository intFloatRepository;
    private final EnumRepository enumRepository;
    private final DictionaryRepository dictionaryRepository;

    public ValidationResult checkRange(String flag, Object value, String dataName) {
        boolean result = false;
        HyperparametersDB curentTable = hyperparameteresRepository.foundRange(flag, dataName);
        System.out.println(curentTable.getId());

        if (value instanceof Integer) {
            IntFloatDB IntFloatRange = intFloatRepository.foundRangeById(curentTable.getIntFloatRange().getId());
            if ((int) value > IntFloatRange.getIntTo() & (int) value < IntFloatRange.getIntFrom()) {
                result = true;
            }
        } else if (value instanceof Double) {
            IntFloatDB IntFloatRange = intFloatRepository.foundRangeById(curentTable.getIntFloatRange().getId());
            if ((double) value > IntFloatRange.getFloatTo() & (double) value < IntFloatRange.getFloatFrom()) {
                result = true;
            }
        } else if (value instanceof String) {
            List<EnumDB> EnumRange = enumRepository.findByHyperparametersDB_Id(curentTable.getId());
            if (EnumRange.stream().map(EnumDB::getEnumValues).toList().contains(value)) {
                result = true;
            }
        }
        return new ValidationResult(result, value, flag);
    }

    public void checkValue(String dataName, List<Parameters> param){
        param.stream()
                .map(e -> checkRange(e.getName(),e.getValue(),dataName))
                .forEach(validationResult -> {
                    if (!validationResult.isResult()){
                        throw new ValueOutOfRange(validationResult.getFlag(),validationResult.getOutOfRangeValue());
                    }
                });
    }
}
