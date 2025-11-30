package com.sivaramanr.mealplanner.converter;

import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import com.sivaramanr.mealplanner.entity.RecipeGroup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeGroupToBeanConverter implements Converter<RecipeGroup, RecipeGroupBean> {

    @Override
    public RecipeGroupBean convert(RecipeGroup recipeGroup) {
        final RecipeGroupBean recipeGroupBean = new RecipeGroupBean();
        recipeGroupBean.setId(recipeGroup.getId());
        recipeGroupBean.setName(recipeGroup.getName());
        recipeGroupBean.setDescription(recipeGroup.getDescription());
        recipeGroupBean.setFoodType(mapFoodType(recipeGroup.getFoodType()));
        return recipeGroupBean;
    }

    protected String mapFoodType(String foodType) {
        return switch (foodType) {
            case "VEG" -> "Vegetarian";
            case "NON_VEG" -> "Non-Vegetarian";
            case "VEGAN" -> "Vegan";
            default -> foodType;
        };
    }

}
