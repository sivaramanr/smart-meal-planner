package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import org.springframework.data.domain.Page;

public interface RecipeGroupService {

    Page<RecipeGroupBean> getRecipeGroups(Integer page, Integer size);

}
