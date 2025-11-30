package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.NutritionBean;
import org.springframework.data.domain.Page;

public interface NutritionService {

    Page<NutritionBean> getNutritions(Integer page, Integer size);

}
