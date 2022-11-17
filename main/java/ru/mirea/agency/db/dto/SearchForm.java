package ru.mirea.agency.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchForm {
    private String text;
    private Double priceMin;
    private Double priceMax;
    private Double pledgeMin;
    private Double pledgeMax;
    private Boolean allowedChildren;
    private Boolean allowedAnimals;
    private Long planId;
    private Long propertyTypeId;
    private Boolean sortPriceDesc;
    private Boolean sortPriceAsc;
    private Boolean sortNew;
    private Long owner;
}
