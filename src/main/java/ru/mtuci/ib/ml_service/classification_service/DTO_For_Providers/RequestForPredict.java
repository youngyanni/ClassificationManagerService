package ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers;

import lombok.*;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RequestForPredict {
    private byte[] model;
    private List<String> matrixAttr;
    private String tag;
}
