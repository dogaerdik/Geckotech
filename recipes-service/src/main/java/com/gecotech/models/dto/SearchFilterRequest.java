package com.gecotech.models.dto;

import com.gecotech.models.entity.Ingredient;
import com.gecotech.models.enums.TypeOfMeal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SearchFilterRequest {
    private String name;
    private String description;
    private String ingredientName;
    private TypeOfMeal typeOfMeal;
}
