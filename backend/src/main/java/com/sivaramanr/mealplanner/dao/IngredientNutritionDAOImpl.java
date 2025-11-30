package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.IngredientNutritionBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import com.sivaramanr.mealplanner.entity.IngredientNutrition;
import com.sivaramanr.mealplanner.entity.Nutrition;
import com.sivaramanr.mealplanner.repository.IngredientNutritionRepository;
import com.sivaramanr.mealplanner.repository.IngredientRepository;
import com.sivaramanr.mealplanner.repository.NutritionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class IngredientNutritionDAOImpl implements IngredientNutritionDAO {

    @Autowired
    private IngredientNutritionRepository ingredientNutritionRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private NutritionRepository nutritionRepository;

    @Override
    public void createIngredientNutrition(IngredientNutritionBean ingredientNutritionBean) {
        IngredientNutrition ingredientNutrition = new IngredientNutrition();
        ingredientNutrition.setId(ingredientNutritionBean.getId());
        Ingredient ingredient = ingredientRepository.findById(ingredientNutritionBean.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ingredient with id " + ingredientNutritionBean.getId() + " does not exist."));
        ingredientNutrition.setIngredient(ingredient);
        Nutrition nutrition = nutritionRepository.findById(ingredientNutritionBean.getNutrition().getId())
                .orElseThrow(() -> new IllegalArgumentException("Nutrition with id " + ingredientNutritionBean.getNutrition().getId() + " does not exist."));
        ingredientNutrition.setNutrition(nutrition);
        ingredientNutrition.setValue(ingredientNutritionBean.getValue());
        ingredientNutritionRepository.save(ingredientNutrition);
    }

    @Override
    public List<IngredientNutrition> getNutritions(Set<String> ingredients) {
        return ingredientNutritionRepository.findByIngredientIdIn(ingredients);
    }

    @Override
    public Page<IngredientNutrition> getIngredientNutritions(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ingredientNutritionRepository.findAll(pageRequest);
    }

}
