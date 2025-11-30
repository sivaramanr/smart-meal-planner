package com.sivaramanr.mealplanner.converter;

import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToBeanConverter implements Converter<Ingredient, IngredientBean> {

    @Override
    public IngredientBean convert(Ingredient ingredient) {
        final IngredientBean ingredientBean = new IngredientBean();
        ingredientBean.setId(ingredient.getId());
        ingredientBean.setCategoryId(ingredient.getIngredientCategory().getId());
        ingredientBean.setName(ingredient.getName());
        ingredientBean.setDescription(ingredient.getDescription());
        ingredientBean.setPrice(ingredient.getPrice());
        return ingredientBean;
    }

}
