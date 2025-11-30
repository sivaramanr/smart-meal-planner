package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.IngredientCategoryDAO;
import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import com.sivaramanr.mealplanner.entity.IngredientCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {

    @Autowired
    private IngredientCategoryDAO ingredientCategoryDAO;

    @Autowired
    private Converter<IngredientCategory, IngredientCategoryBean> converter;

    @Override
    public Page<IngredientCategoryBean> getIngredientCategories(Integer page, Integer size) {
        return ingredientCategoryDAO.getIngredientCategories(page, size).map(converter::convert);
    }

}
