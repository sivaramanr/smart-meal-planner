package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.dao.RecipeDAO;
import com.sivaramanr.mealplanner.dto.RecipeBean;
import com.sivaramanr.mealplanner.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private Converter<Recipe, RecipeBean> converter;

    @Override
    public List<RecipeBean> findAllByName(String name) {
        return recipeDAO.findAllByName(name).stream().map(converter::convert).toList();
    }

    @Override
    public Page<RecipeBean> getRecipes(Integer page, Integer size) {
        return recipeDAO.getRecipes(page, size).map(converter::convert);
    }

}
