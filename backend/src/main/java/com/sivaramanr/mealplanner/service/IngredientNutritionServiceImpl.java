package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.IngredientNutritionDAO;
import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import com.sivaramanr.mealplanner.entity.IngredientNutrition;
import com.sivaramanr.mealplanner.entity.Nutrition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class IngredientNutritionServiceImpl implements IngredientNutritionService{

    @Autowired
    private IngredientNutritionDAO ingredientNutritionDAO;

    @Autowired
    private Converter<Ingredient, IngredientBean> ingredientBeanConverter;

    @Autowired
    private Converter<Nutrition, NutritionBean> nutritionBeanConverter;

    @Override
    public List<NutritionBean> getIngredientNutritions(Map<String, String> ingredientsQuantityMap) {
        List<NutritionBean> nutritionBeans = new ArrayList<>();
        List<IngredientNutrition> ingredientNutritions = ingredientNutritionDAO.getNutritions(ingredientsQuantityMap.keySet());
        for (IngredientNutrition ingredientNutrition : ingredientNutritions) {
            Nutrition nutrition = ingredientNutrition.getNutrition();
            String strIngredientQuantity = ofNullable(ingredientsQuantityMap.get(ingredientNutrition.getIngredient().getId())).orElse("0");

            Float ingredientQuantity;
            try {
                ingredientQuantity = Float.parseFloat(strIngredientQuantity);
            } catch (NumberFormatException e) {
                log.warn("Invalid quantity '{}' for ingredient id '{}'. Defaulting to 0.", strIngredientQuantity, ingredientNutrition.getIngredient().getId());
                ingredientQuantity = 0.0f;
            }
            Float nutritionValue = ofNullable(ingredientNutrition.getValue()).orElse(0.0f);
            Float totalNutritionValue = (ingredientQuantity / 100) * nutritionValue;
            NutritionBean nutritionBean = nutritionBeanConverter.convert(nutrition);
            nutritionBean.setValue(totalNutritionValue);
            nutritionBeans.add(nutritionBean);
        }
        return nutritionBeans;
    }

}
