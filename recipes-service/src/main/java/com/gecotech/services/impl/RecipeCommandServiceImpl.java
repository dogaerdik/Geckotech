package com.gecotech.services.impl;

import com.gecotech.exceptions.RecipeNotFoundException;
import com.gecotech.models.dto.RecipeDTO;
import com.gecotech.models.entity.Ingredient;
import com.gecotech.models.entity.Recipe;
import com.gecotech.models.repositories.RecipeRepository;
import com.gecotech.services.RecipeCommandService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeCommandServiceImpl  implements RecipeCommandService {
    private final ModelMapper mapper;
    private final RecipeRepository recipeRepository;
    @Override
    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        return Optional.of(mapper.map(recipeDTO, Recipe.class))
                .map(recipeRepository::save)
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public RecipeDTO updateRecipe(RecipeDTO request,Long id) throws RecipeNotFoundException{

        Recipe recipe =recipeRepository.findById(id)
                .orElseThrow(() ->new RecipeNotFoundException("Not found recipe id-" + id));
        List<Ingredient> ingredientList = new ArrayList<>();
        request.getIngredients().forEach(ingredient1 ->ingredientList.add(ingredient1));
        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(ingredientList);
        recipe.setName(request.getName());
        recipe.setDescription(request.getDescription());
        recipe.setTimeRequired(request.getTimeRequired());
        recipe.setTypeOfMeal(request.getTypeOfMeal());

        return Optional.of(mapper.map(recipe, Recipe.class))
                .map(recipeMap->recipeRepository.save(recipeMap))
                .map(recipeMap -> mapper.map(recipeMap, RecipeDTO.class))
                .orElse(null);
    }
}
