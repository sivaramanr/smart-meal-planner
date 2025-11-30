package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

public interface IngredientDAO {

    void createIngredient(IngredientBean ingredientBean);

    Ingredient getIngredient(String id);

    Page<Ingredient> getIngredients(Integer page, Integer size);

}
