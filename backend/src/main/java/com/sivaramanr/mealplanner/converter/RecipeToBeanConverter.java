package com.sivaramanr.mealplanner.converter;

import com.sivaramanr.mealplanner.controller.Constants;
import com.sivaramanr.mealplanner.dto.RecipeBean;
import com.sivaramanr.mealplanner.entity.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class RecipeToBeanConverter implements Converter<Recipe, RecipeBean> {

    @Override
    public RecipeBean convert(Recipe recipeGroup) {
        final RecipeBean recipeBean = new RecipeBean();
        recipeBean.setId(recipeGroup.getId());
        recipeBean.setGroupId(recipeGroup.getId());
        recipeBean.setName(recipeGroup.getName());
        recipeBean.setPortion(recipeGroup.getPortion());
        ofNullable(Constants.UOM.get(recipeGroup.getUom())).ifPresent(recipeBean::setUom);
        recipeBean.setDescription(recipeGroup.getDescription());
        recipeBean.setFoodType(mapFoodType(recipeGroup.getFoodType()));
        return recipeBean;
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
