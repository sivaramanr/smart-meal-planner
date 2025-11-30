package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.NutritionBean;

import java.util.List;
import java.util.Map;

public interface IngredientNutritionService {

    List<NutritionBean> getIngredientNutritions(Map<String, String> ingredientsQuantityMap);

}
