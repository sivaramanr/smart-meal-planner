package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import org.springframework.data.domain.Page;

public interface IngredientCategoryService {

    Page<IngredientCategoryBean> getIngredientCategories(Integer page, Integer size);

}
