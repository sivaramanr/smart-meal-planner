package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.IngredientDAO;
import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private Converter<Ingredient, IngredientBean> converter;

    @Override
    public IngredientBean getIngredient(String id) {
        Ingredient ingredient = ingredientDAO.getIngredient(id);
        IngredientBean ingredientBean = converter.convert(ingredient);
        List<IngredientBean.NutritionValue> nutritions = new ArrayList<>();
        ingredient.getNutritions().forEach(nutrition -> {
            IngredientBean.NutritionValue nutritionValue = new IngredientBean.NutritionValue();
            nutritionValue.setNutrition(nutrition.getNutrition().getName());
            nutritionValue.setUom(nutrition.getNutrition().getUom());
            nutritionValue.setValue(nutrition.getValue());
            nutritions.add(nutritionValue);
        });
        ingredientBean.setNutritions(nutritions);
        return ingredientBean;
    }

    @Override
    public Page<IngredientBean> getIngredients(Integer page, Integer size) {
        return ingredientDAO.getIngredients(page, size).map(converter::convert);
    }

}
