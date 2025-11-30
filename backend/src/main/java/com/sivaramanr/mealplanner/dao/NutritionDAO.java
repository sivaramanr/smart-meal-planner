package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.entity.Nutrition;
import org.springframework.data.domain.Page;

public interface NutritionDAO {

    void createNutrition(NutritionBean nutrition);

    Page<Nutrition> getNutritions(Integer page, Integer size);

}
