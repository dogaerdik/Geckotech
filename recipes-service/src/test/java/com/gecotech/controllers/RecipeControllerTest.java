package com.gecotech.controllers;

import com.gecotech.models.dto.RecipeDTO;
import com.gecotech.models.dto.SearchFilterRequest;
import com.gecotech.models.entity.Ingredient;
import com.gecotech.models.enums.TypeOfMeal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private String buildApiPath(String endPoint) {
        String baseURL = "http://localhost:"+port+"/v1/recipe";
        String apiPath = baseURL+endPoint;
        return apiPath;
    }

    @Test
    @DisplayName("Create a new recipe")
    void givenOneRecipe_whenCallingCreateRecipe_thenReturnCreatedRecipe() {
        //Given
        HttpHeaders headers = new HttpHeaders();

        RecipeDTO createRecipeDto = new RecipeDTO();
        Ingredient ingredientDto = new Ingredient();
        List<Ingredient> ingredientList = new ArrayList<>();
        createRecipeDto.setName("Banana Bread");
        createRecipeDto.setDescription("This banana bread recipe creates the most delicious, moist loaf");
        ingredientDto.setName("eggs");
        ingredientDto.setAmount("5 pieces");
        ingredientList.add(ingredientDto);
        createRecipeDto.setIngredients(ingredientList);
        createRecipeDto.setTimeRequired("45 minutes");
        createRecipeDto.setTypeOfMeal(TypeOfMeal.BREAKFAST);
        HttpEntity<RecipeDTO> requestEntity = new HttpEntity<>(createRecipeDto, headers);

        //When
        ResponseEntity<RecipeDTO> response = testRestTemplate.exchange(
                buildApiPath(""),
                HttpMethod.POST,
                requestEntity,
                RecipeDTO.class
        );

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Banana Bread");
        assertThat(Objects.requireNonNull(response.getBody()).getTimeRequired()).isEqualTo("45 minutes");
        assertThat(Objects.requireNonNull(response.getBody()).getTypeOfMeal()).isEqualTo(TypeOfMeal.BREAKFAST);
        assertThat(Objects.requireNonNull(response.getBody()).getIngredients().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Modify a recipe")
    void givenRecipeInfo_WhenCallingModifyRecipe_ThenSucceed() {
        //Given
        HttpHeaders headers = new HttpHeaders();

        RecipeDTO createRecipeDto = new RecipeDTO();
        Ingredient ingredientDto = new Ingredient();
        List<Ingredient> ingredientList = new ArrayList<>();
        createRecipeDto.setName("Chicken curry");
        createRecipeDto.setDescription("Chicken curry more spicy version");
        ingredientDto.setName("eggs");
        ingredientDto.setAmount("5 pieces");
        ingredientList.add(ingredientDto);
        createRecipeDto.setIngredients(ingredientList);
        createRecipeDto.setTimeRequired("45 minutes");
        createRecipeDto.setTypeOfMeal(TypeOfMeal.BREAKFAST);
        HttpEntity<RecipeDTO> requestEntity = new HttpEntity<>(createRecipeDto, headers);

        ResponseEntity<RecipeDTO> initResponse = testRestTemplate.exchange(
                buildApiPath(""),
                HttpMethod.POST,
                requestEntity,
                RecipeDTO.class
        );

        RecipeDTO updateRecipeDto = new RecipeDTO();
        updateRecipeDto.setName(Objects.requireNonNull(initResponse.getBody()).getName());
        updateRecipeDto.setDescription(Objects.requireNonNull(initResponse.getBody()).getDescription());
        updateRecipeDto.setIngredients(initResponse.getBody().getIngredients());
        updateRecipeDto.setTimeRequired(Objects.requireNonNull(initResponse.getBody()).getTimeRequired());
        updateRecipeDto.setTypeOfMeal(TypeOfMeal.LUNCH);

        //When
        ResponseEntity<RecipeDTO> response = testRestTemplate.exchange(
                buildApiPath("/" + 1),
                HttpMethod.PUT,
                new HttpEntity<>(updateRecipeDto, headers),
                RecipeDTO.class
        );

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getTypeOfMeal()).isEqualTo(TypeOfMeal.LUNCH);
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Chicken curry");
        assertThat(Objects.requireNonNull(response.getBody()).getTimeRequired()).isEqualTo("45 minutes");
        assertThat(Objects.requireNonNull(response.getBody()).getIngredients().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Find a recipe")
    void givenOneRecipeId_whenCallingRetrieveRecipe_thenReturnOneRecipe() {

        //Given
        HttpHeaders headers = new HttpHeaders();

        RecipeDTO createRecipeDto = new RecipeDTO();
        Ingredient ingredientDto = new Ingredient();
        List<Ingredient> ingredientList = new ArrayList<>();
        createRecipeDto.setName("Chicken curry");
        createRecipeDto.setDescription("Chicken curry more spicy version");
        ingredientDto.setName("eggs");
        ingredientDto.setAmount("5 pieces");
        ingredientList.add(ingredientDto);
        createRecipeDto.setIngredients(ingredientList);
        createRecipeDto.setTimeRequired("45 minutes");
        createRecipeDto.setTypeOfMeal(TypeOfMeal.BREAKFAST);
        HttpEntity<RecipeDTO> requestEntity = new HttpEntity<>(createRecipeDto, headers);

        ResponseEntity<RecipeDTO> initResponse = testRestTemplate.exchange(
                buildApiPath(""),
                HttpMethod.POST,
                requestEntity,
                RecipeDTO.class
        );

        //When
        ResponseEntity<RecipeDTO> response = testRestTemplate.exchange(
                buildApiPath("/" + 1),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                RecipeDTO.class
        );

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getTypeOfMeal()).isEqualTo(TypeOfMeal.BREAKFAST);
    }

    @Test
    @DisplayName("Search recipes")
    void givenRecipeRequest_whenCallingFilterRecipe_thenReturnSearchedRecipes() {

        //Given
        HttpHeaders headers = new HttpHeaders();

        RecipeDTO createRecipeDto = new RecipeDTO();
        Ingredient ingredientDto = new Ingredient();
        List<Ingredient> ingredientList = new ArrayList<>();
        createRecipeDto.setName("Chicken curry");
        createRecipeDto.setDescription("Chicken curry more spicy version");
        ingredientDto.setName("eggs");
        ingredientDto.setAmount("5 pieces");
        ingredientList.add(ingredientDto);
        createRecipeDto.setIngredients(ingredientList);
        createRecipeDto.setTimeRequired("45 minutes");
        createRecipeDto.setTypeOfMeal(TypeOfMeal.BREAKFAST);
        HttpEntity<RecipeDTO> requestEntity = new HttpEntity<>(createRecipeDto, headers);

        ResponseEntity<RecipeDTO> initResponse = testRestTemplate.exchange(
                buildApiPath(""),
                HttpMethod.POST,
                requestEntity,
                RecipeDTO.class
        );
        SearchFilterRequest searchFilterRequest = new SearchFilterRequest();
        searchFilterRequest.setName("Chicken curry");
        searchFilterRequest.setDescription("Chicken curry more spicy version");
        searchFilterRequest.setIngredientName("eggs");
        searchFilterRequest.setTypeOfMeal(TypeOfMeal.BREAKFAST);
        //When
        ResponseEntity<RecipeDTO[]> response = testRestTemplate.exchange(
                buildApiPath("/search"),
                HttpMethod.POST,
                new HttpEntity<>(searchFilterRequest, headers),
                RecipeDTO[].class
        );

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()[0]).getTypeOfMeal()).isEqualTo(TypeOfMeal.BREAKFAST);
        assertThat(Objects.requireNonNull(response.getBody()[0]).getName()).isEqualTo("Chicken curry");
        assertThat(Objects.requireNonNull(response.getBody()[0]).getTimeRequired()).isEqualTo("45 minutes");
        assertThat(Objects.requireNonNull(response.getBody()[0]).getIngredients().size()).isEqualTo(1);
    }
}