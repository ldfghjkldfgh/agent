package ru.mirea.agency.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.agency.db.validation.NotNegative;

@NotNegative(
        fields = {"price"},
        message = "Неправильная цена")
@NotNegative(
        fields = {"planId"},
        notZero = true,
        message = "Неправильный план")
@Data
@NoArgsConstructor
public class PostForm {
    private String name;
    private String description;
    private Double price;
    private Long planId;
    private Long pledge;
    private Boolean allowedChildren = false;
    private Boolean allowedAnimals = false;
}
