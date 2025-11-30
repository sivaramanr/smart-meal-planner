package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, String>,
        JpaSpecificationExecutor<IngredientCategory> {
}
