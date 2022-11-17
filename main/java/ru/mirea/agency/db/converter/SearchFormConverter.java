package ru.mirea.agency.db.converter;

import ru.mirea.agency.db.dto.SearchForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchFormConverter {
    public SearchForm mapToSearchForm(Map<String, String> mp) {
        SearchForm searchForm = new SearchForm();
        searchForm.setText(mp.getOrDefault("query", null));
        List<String> numsStrings = Arrays.asList(
                mp.getOrDefault("priceMin", null),
                mp.getOrDefault("priceMax", null),
                mp.getOrDefault("pledgeMin", null),
                mp.getOrDefault("pledgeMax", null)
        );
        List<Double> nums = new ArrayList<>(4);
        int index = 0;
        for (String num : numsStrings) {
            if (num != null) {
                Double value = isNumber(num);
                if (value == null) {
                    return null;
                }
                if (index == 0) {
                    searchForm.setPriceMin(value);
                } else if (index == 1) {
                    searchForm.setPriceMax(value);
                } else if (index == 2) {
                    searchForm.setPledgeMin(value);
                } else if (index == 3) {
                    searchForm.setPledgeMax(value);
                }
            }
            index++;
        }
//        searchForm.setAllowedAnimals(mp.getOrDefault("allowedAnimals", null));
//        searchForm.setAllowedChildren(mp.getOrDefault("allowedChildren", null));
        return searchForm;
    }

    private Double isNumber(String text) {
        try {
            return Double.valueOf(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
