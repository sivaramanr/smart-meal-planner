package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.FoodSession;
import com.sivaramanr.mealplanner.entity.RecipeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeGroupRepository extends JpaRepository<RecipeGroup, String>, JpaSpecificationExecutor<RecipeGroup> {
}
