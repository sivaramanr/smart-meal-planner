package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NutritionRepository extends JpaRepository<Nutrition, String>, JpaSpecificationExecutor<Nutrition> {
}
