package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.NutritionDAO;
import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.entity.Nutrition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NutritionServiceImpl implements NutritionService{

    @Autowired
    private NutritionDAO nutritionDAO;

    @Autowired
    private Converter<Nutrition, NutritionBean> converter;

    @Override
    public Page<NutritionBean> getNutritions(Integer page, Integer size) {
        return nutritionDAO.getNutritions(page, size).map(converter::convert);
    }

}
