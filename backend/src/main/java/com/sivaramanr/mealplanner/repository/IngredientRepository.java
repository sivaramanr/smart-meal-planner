package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.Ingredient;
import com.sivaramanr.mealplanner.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientRepository extends JpaRepository<Ingredient, String>, JpaSpecificationExecutor<Ingredient> {

    @Query("SELECT i FROM Ingredient i LEFT JOIN FETCH i.nutritions WHERE i.id = :id")
    Ingredient findByIdWithNutritions(@Param("id") String id);

}
