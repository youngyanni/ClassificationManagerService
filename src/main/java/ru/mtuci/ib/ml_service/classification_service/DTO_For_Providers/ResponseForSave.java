package ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseForSave {
    private byte[] model;
    private String tag;
}
