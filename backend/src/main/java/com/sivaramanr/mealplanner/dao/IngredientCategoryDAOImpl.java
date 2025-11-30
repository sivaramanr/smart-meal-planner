package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import com.sivaramanr.mealplanner.entity.IngredientCategory;
import com.sivaramanr.mealplanner.repository.IngredientCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class IngredientCategoryDAOImpl implements IngredientCategoryDAO{

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public void createIngredientCategory(IngredientCategoryBean ingredientCategoryBean) {
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setId(ofNullable(ingredientCategoryBean.getId()).orElseGet(UUID.randomUUID()::toString));
        ingredientCategory.setName(ingredientCategoryBean.getName());
        ingredientCategory.setDescription(ingredientCategoryBean.getDescription());
        ingredientCategoryRepository.save(ingredientCategory);
        log.debug("Created Ingredient Category with ID: {}", ingredientCategory.getId());
    }

    @Override
    public Page<IngredientCategory> getIngredientCategories(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ingredientCategoryRepository.findAll(pageRequest);
    }

}
