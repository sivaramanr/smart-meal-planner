package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String>, JpaSpecificationExecutor<Recipe> {

    List<Recipe> findByNameContaining(String name);

}
