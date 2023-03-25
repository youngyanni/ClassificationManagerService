package ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers;

import lombok.*;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RequestForTrain {
    private List<Object> matrixAttrTrain;
    private List<Object> matrixAttrTest;
    private List<String> labels;
    private byte [] model;
    private String tag;

}
