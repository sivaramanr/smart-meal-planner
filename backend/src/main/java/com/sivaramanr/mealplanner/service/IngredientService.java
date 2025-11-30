package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import org.springframework.data.domain.Page;

public interface IngredientService {

    IngredientBean getIngredient(String id);

    Page<IngredientBean> getIngredients(Integer page, Integer size);

}
