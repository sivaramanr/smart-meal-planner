package com.sivaramanr.mealplanner.converter;

import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import com.sivaramanr.mealplanner.entity.IngredientCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCategoryToBeanConverter implements Converter<IngredientCategory, IngredientCategoryBean> {

    @Override
    public IngredientCategoryBean convert(IngredientCategory ingredientCategory) {
        final IngredientCategoryBean ingredientCategoryBean = new IngredientCategoryBean();
        ingredientCategoryBean.setId(ingredientCategory.getId());
        ingredientCategoryBean.setName(ingredientCategory.getName());
        ingredientCategoryBean.setDescription(ingredientCategory.getDescription());
        return ingredientCategoryBean;
    }

}
