package com.gecotech.services.spec;

import com.gecotech.models.dto.SearchFilterRequest;
import com.gecotech.models.entity.Ingredient;
import com.gecotech.models.entity.Recipe;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class SearchFilterSpec {
    public static Specification<Recipe> searchRecipeInfo(SearchFilterRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null) {
                predicates.add(cb.equal(root.get("name"), request.getName()));
            }
            if (request.getTypeOfMeal() != null) {
                predicates.add(cb.equal(root.get("typeOfMeal"), request.getTypeOfMeal()));
            }
            if (request.getDescription() != null) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%"+request.getDescription().toLowerCase()+"%"));
            }
            if (request.getIngredientName() != null) {
                Join<Ingredient, Recipe> ingredientRecipeJoin = root.join("ingredients");
                predicates.add(cb.equal(ingredientRecipeJoin.get("name"), request.getIngredientName()));
            }
            query.orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    public static Specification<Recipe> searchRecipe(SearchFilterRequest request) {
        return Specification.where(searchRecipeInfo(request));
    }
}
