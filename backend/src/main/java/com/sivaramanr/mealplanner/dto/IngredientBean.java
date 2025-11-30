package com.sivaramanr.mealplanner.dto;

import lombok.Data;

import java.util.List;

@Data
public class IngredientBean {

    private String id;

    private String categoryId;

    private String name;

    private String description;

    private Float price;

    private List<NutritionValue> nutritions;

    @Data
    public static class NutritionValue {

        private String nutrition;

        private String uom;

        private Float value;

    }

}
