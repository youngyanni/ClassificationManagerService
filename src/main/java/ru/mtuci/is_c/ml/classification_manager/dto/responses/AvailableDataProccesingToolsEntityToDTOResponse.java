package ru.mtuci.is_c.ml.classification_manager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.is_c.ml.classification_manager.dto.algorithms.GeneralDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableDataProccesingToolsEntityToDTOResponse {
    private String providerName;
    private List<GeneralDTO> algList;
    private List<GeneralDTO> encoderList;
    private List<GeneralDTO> scalersList;
}
