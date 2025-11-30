package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.IngredientNutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface IngredientNutritionRepository extends JpaRepository<IngredientNutrition, String>,
        JpaSpecificationExecutor<IngredientNutrition> {

    List<IngredientNutrition> findByIngredientIdIn(Set<String> ingredientIds);

}
