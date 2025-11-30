package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.entity.Ingredient;
import com.sivaramanr.mealplanner.repository.IngredientCategoryRepository;
import com.sivaramanr.mealplanner.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class IngredientDAOImpl implements IngredientDAO {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public void createIngredient(IngredientBean ingredientBean) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ofNullable(ingredientBean.getId()).orElseGet(UUID.randomUUID()::toString));

        ingredientCategoryRepository
            .findById(ingredientBean.getCategoryId())
            .ifPresent(ingredient::setIngredientCategory);

        ingredient.setName(ingredientBean.getName());
        ingredient.setDescription(ingredientBean.getDescription());
        ingredient.setPrice(ingredientBean.getPrice());
        ingredientRepository.save(ingredient);
        log.debug("Created Ingredient with ID: {}", ingredient.getId());
    }

    @Override
    public Ingredient getIngredient(String id) {
        return ingredientRepository.findByIdWithNutritions(id);
    }

    @Override
    public Page<Ingredient> getIngredients(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ingredientRepository.findAll(pageRequest);
    }
}
