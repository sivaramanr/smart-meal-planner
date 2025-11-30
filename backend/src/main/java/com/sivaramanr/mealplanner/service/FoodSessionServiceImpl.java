package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.FoodSessionDAO;
import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.entity.FoodSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FoodSessionServiceImpl implements FoodSessionService {

    @Autowired
    private FoodSessionDAO foodSessionDAO;

    @Autowired
    private Converter<FoodSession, FoodSessionBean> converter;

    @Override
    public Page<FoodSessionBean> getFoodSessions(Integer page, Integer size) {
        return foodSessionDAO.getSessions(page, size).map(converter::convert);
    }

}
