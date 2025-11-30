package com.sivaramanr.mealplanner.dto;

import lombok.Data;

@Data
public class IngredientNutritionBean {

    private String id;

    private IngredientBean ingredient;

    private NutritionBean nutrition;

    private Float value;

}
