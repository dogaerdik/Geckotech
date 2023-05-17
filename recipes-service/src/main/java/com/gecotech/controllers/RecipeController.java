package com.gecotech.controllers;

import com.gecotech.exceptions.RecipeNotFoundException;
import com.gecotech.models.dto.RecipeDTO;
import com.gecotech.models.dto.SearchFilterRequest;
import com.gecotech.services.RecipeCommandService;
import com.gecotech.services.RecipeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/v1/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeController {
    private final RecipeCommandService recipeCommandService;
    private final RecipeQueryService recipeQueryService;
    @PostMapping
    @Operation(summary = "Create a new recipe")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description =  "Successfully, recipe was created"),
            @ApiResponse(responseCode = "404", description =  "User not found")})
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        log.info("Creating the recipe with properties");
        return new ResponseEntity<>(recipeCommandService.createRecipe(recipeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify the recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Successfully, recipe was modified"),
            @ApiResponse(responseCode = "404", description =  "Recipe not found")
    })
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody RecipeDTO recipeDTO, @PathVariable Long id) throws RecipeNotFoundException {
        log.info("Updating the recipe with id "+ id);
        return new ResponseEntity<>(recipeCommandService.updateRecipe(recipeDTO, id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one recipe")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description =  "Successfully, one recipe was fetched"),
            @ApiResponse(responseCode = "404", description =  "Recipe not found") })
    public ResponseEntity<RecipeDTO> retrieveRecipe(@PathVariable Long id) throws RecipeNotFoundException {
        return new ResponseEntity<>(recipeQueryService.findOneRecipe(id),HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Different error messages related to criteria and recipe")

    })
    @PostMapping(value = "/search")
    public ResponseEntity<List<RecipeDTO>> searchRecipe(@RequestBody SearchFilterRequest searchFilterRequest) {
        return new ResponseEntity<>(recipeQueryService.searchRecipe(searchFilterRequest), HttpStatus.OK);
    }
}
