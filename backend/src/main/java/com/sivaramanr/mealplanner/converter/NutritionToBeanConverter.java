package com.sivaramanr.mealplanner.converter;

import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.entity.Nutrition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NutritionToBeanConverter implements Converter<Nutrition, NutritionBean> {

    @Override
    public NutritionBean convert(Nutrition recipeGroup) {
        final NutritionBean nutritionBean = new NutritionBean();
        nutritionBean.setId(recipeGroup.getId());
        nutritionBean.setCategory(recipeGroup.getCategory());
        nutritionBean.setName(recipeGroup.getName());
        nutritionBean.setDescription(recipeGroup.getDescription());
        nutritionBean.setUom(recipeGroup.getUom());
        return nutritionBean;
    }

}
