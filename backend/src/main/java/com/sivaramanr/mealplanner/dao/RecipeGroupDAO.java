package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import com.sivaramanr.mealplanner.entity.RecipeGroup;
import org.springframework.data.domain.Page;

public interface RecipeGroupDAO {

    void createRecipeGroup(RecipeGroupBean recipeGroupBean);

    Page<RecipeGroup> getRecipeGroups(Integer page, Integer size);

}
