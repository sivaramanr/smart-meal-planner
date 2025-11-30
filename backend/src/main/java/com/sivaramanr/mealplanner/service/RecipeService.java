package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.RecipeBean;
import com.sivaramanr.mealplanner.entity.Recipe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeService {

    List<RecipeBean> findAllByName(String name);

    Page<RecipeBean> getRecipes(Integer page, Integer size);

}
