package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.RecipeBean;
import com.sivaramanr.mealplanner.entity.Recipe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeDAO {

    void createRecipe(RecipeBean recipeBean);

    List<Recipe> findAllByName(String name);

    Page<Recipe> getRecipes(Integer page, Integer size);

}
