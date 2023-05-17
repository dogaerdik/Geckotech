package com.gecotech.models.dto;

import com.gecotech.models.entity.Ingredient;
import com.gecotech.models.enums.TypeOfMeal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import java.util.List;

@Data
public class RecipeDTO {
    @NotBlank(message = "Please provide the recipe name")
    @Size(max = 100, message = "The name can be {max} characters long at maximum")
    @Schema(description = "The name of the recipe", example = "Basic Muffin Recipe", name= "name",required = true)
    private String name;
    @NotBlank(message = "Please provide the recipe description")
    @Schema(description = "The information of the recipe description", example = "Heat oven to 200C. Line 2 muffin trays with paper muffin cases...", name= "description",required = true)
    @Size(max = 500, message = "The recipe description can be {max} characters long at maximum")
    private String description;
    @NotBlank(message = "Please provide the recipe ingredients")
    @Schema(description = "The ids of the ingredients needed to make the recipe", example = "[{\"name\" :\"eggs\" , \"amount\": \"2 medium\"}, {\"name\" : \"vegetable oil\" , \"amount\" : \"125ml\" },{ \"name\" : \"chocolate chips\" , \"amount\" : \"100g\" }]", name= "ingredients",required = true)
    private List<Ingredient> ingredients;
    @NotBlank(message = "Please provide the time required to cook")
    @Schema(description = "The information of the recipe time required to cook", example = "35 minutes", name= "timeRequired",required = true)
    private String timeRequired;
    @Schema(description = "The information type of meal", example = "LUNCH", name= "typeOfMeal",required = false)
    private TypeOfMeal typeOfMeal;
}