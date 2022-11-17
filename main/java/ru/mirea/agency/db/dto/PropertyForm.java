package ru.mirea.agency.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.agency.db.validation.NotNegative;

@NotNegative(
        fields = {"area"},
        notZero = true,
        message = "Неправильная площадь")
@NotNegative(
        fields = {"propertyTypeId"},
        notZero = true,
        message = "Неправильный тип")
@Data
@NoArgsConstructor
public class PropertyForm {
    private Long id;
    private Long propertyTypeId;
    private Double area;
}
