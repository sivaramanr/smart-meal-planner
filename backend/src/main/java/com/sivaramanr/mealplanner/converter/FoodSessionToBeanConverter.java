package com.sivaramanr.mealplanner.converter;

import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.entity.FoodSession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FoodSessionToBeanConverter implements Converter<FoodSession, FoodSessionBean> {

    @Override
    public FoodSessionBean convert(FoodSession foodSession) {
        final FoodSessionBean foodSessionBean = new FoodSessionBean();
        foodSessionBean.setId(foodSession.getId());
        foodSessionBean.setName(foodSession.getName());
        foodSessionBean.setDescription(foodSession.getDescription());
        foodSessionBean.setTiming(foodSession.getTiming());
        return foodSessionBean;
    }

}
