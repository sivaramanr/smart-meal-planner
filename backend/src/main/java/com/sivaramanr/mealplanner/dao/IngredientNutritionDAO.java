package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.IngredientNutritionBean;
import com.sivaramanr.mealplanner.entity.IngredientNutrition;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface IngredientNutritionDAO {

    void createIngredientNutrition(IngredientNutritionBean ingredientNutritionBean);

    List<IngredientNutrition> getNutritions(Set<String> ingredients);

    Page<IngredientNutrition> getIngredientNutritions(Integer page, Integer size);

}
