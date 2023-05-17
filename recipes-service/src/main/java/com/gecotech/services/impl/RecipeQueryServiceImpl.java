package com.gecotech.services.impl;

import com.gecotech.exceptions.RecipeNotFoundException;
import com.gecotech.models.dto.RecipeDTO;
import com.gecotech.models.dto.SearchFilterRequest;
import com.gecotech.models.entity.Recipe;
import com.gecotech.models.repositories.RecipeRepository;
import com.gecotech.services.RecipeQueryService;
import com.gecotech.services.spec.SearchFilterSpec;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeQueryServiceImpl implements RecipeQueryService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper mapper;
    @Override
    public RecipeDTO findOneRecipe(Long id) throws RecipeNotFoundException {
        return mapper.map(recipeRepository.findById(id).orElseThrow(() ->new RecipeNotFoundException("Not found recipe id-" + id)),RecipeDTO.class);
    }

    @Override
    public List<RecipeDTO> searchRecipe(SearchFilterRequest searchFilterRequest) {
        List<Recipe> recipeList= recipeRepository.findAll(SearchFilterSpec.searchRecipe(searchFilterRequest));
        return recipeList.stream().map(recipe -> mapper.map(recipe,RecipeDTO.class)).collect(Collectors.toList());
    }
}
