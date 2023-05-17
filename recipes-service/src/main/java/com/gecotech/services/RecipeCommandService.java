package com.gecotech.services;

import com.gecotech.exceptions.RecipeNotFoundException;
import com.gecotech.models.dto.RecipeDTO;

public interface RecipeCommandService {
    RecipeDTO createRecipe(RecipeDTO recipeDTO);
    RecipeDTO updateRecipe(RecipeDTO request,Long id)  throws RecipeNotFoundException;;
}
