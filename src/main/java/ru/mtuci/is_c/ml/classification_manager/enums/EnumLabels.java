package ru.mtuci.is_c.ml.classification_manager.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumLabels {
    CREATE("Создать модель"),
    TRAIN("Обучить модель"),
    PREDICT("Получить предсказание"),
    SENT_FOR_CREATE("Отправлена на создание"),
    SENT_FOR_TRAIN("Отправлена на обучение"),
    SENT_FOR_PREDICT("Отправлена на предсказание"),
    CREATED("Создана"),
    TRAINED("Обучена"),
    PREDICTED("Предсказала");
    private final String description;

    public String getDescription() {
        return this.description;
    }
}
