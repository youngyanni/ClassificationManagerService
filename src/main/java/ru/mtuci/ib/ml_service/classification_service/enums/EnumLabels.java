package ru.mtuci.ib.ml_service.classification_service.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumLabels {
    CREATE("Создать модель"),
    TRAIN("Обучить модель"),
    PREDICT("Получить предсказание"),
    SENTFORCREATE("Отправлена на создание"),
    SENTFORTRAIN("Отправлена на обучение"),
    SENTFORPREDICT("Отправлена на предсказание"),
    CREATED("Создана"),
    TRAINED("Обучена"),
    PREDICTED("Предсказала");
    private final String descript;

    public String getDescript() {
        return this.descript;
    }
}
