package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.entity.FoodSession;
import org.springframework.data.domain.Page;

public interface FoodSessionDAO {

    void createSession(FoodSessionBean foodSessionBean);

    Page<FoodSession> getSessions(Integer page, Integer size);

}
