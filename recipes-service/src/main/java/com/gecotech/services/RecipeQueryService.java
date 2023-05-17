package com.gecotech.services;

import com.gecotech.exceptions.RecipeNotFoundException;
import com.gecotech.models.dto.RecipeDTO;
import com.gecotech.models.dto.SearchFilterRequest;

import java.util.List;

public interface RecipeQueryService {
    RecipeDTO findOneRecipe(Long id) throws RecipeNotFoundException;
    List<RecipeDTO> searchRecipe(SearchFilterRequest searchFilterRequest);
}
