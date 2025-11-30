package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import org.springframework.data.domain.Page;

public interface FoodSessionService {

    Page<FoodSessionBean> getFoodSessions(Integer page, Integer size);

}
