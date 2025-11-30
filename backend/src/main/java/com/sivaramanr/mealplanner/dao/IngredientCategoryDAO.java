package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import com.sivaramanr.mealplanner.entity.IngredientCategory;
import org.springframework.data.domain.Page;

public interface IngredientCategoryDAO {

    void createIngredientCategory(IngredientCategoryBean ingredientCategoryBean);

    Page<IngredientCategory> getIngredientCategories(Integer page, Integer size);

}
