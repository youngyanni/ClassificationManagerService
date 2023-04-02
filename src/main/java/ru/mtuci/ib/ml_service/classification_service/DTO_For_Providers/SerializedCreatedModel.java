package ru.mtuci.ib.ml_service.classification_service.DTO_For_Providers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SerializedCreatedModel {
    private String createdModel;
}
