package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.RecipeGroupDAO;
import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import com.sivaramanr.mealplanner.entity.RecipeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RecipeGroupServiceImpl implements RecipeGroupService {

    @Autowired
    private RecipeGroupDAO recipeGroupDAO;

    @Autowired
    private Converter<RecipeGroup, RecipeGroupBean> converter;

    @Override
    public Page<RecipeGroupBean> getRecipeGroups(Integer page, Integer size) {
        return recipeGroupDAO.getRecipeGroups(page, size).map(converter::convert);
    }

}
