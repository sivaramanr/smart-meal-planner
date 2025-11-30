package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.RecipeBean;
import com.sivaramanr.mealplanner.entity.Recipe;
import com.sivaramanr.mealplanner.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class RecipeDAOImpl implements RecipeDAO {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public void createRecipe(RecipeBean recipeBean) {
        Recipe recipe = new Recipe();
        recipe.setId(ofNullable(recipeBean.getId()).orElseGet(UUID.randomUUID()::toString));
        recipe.setGroupId(recipeBean.getGroupId());
        recipe.setName(recipeBean.getName());
        recipe.setPortion(recipeBean.getPortion());
        recipe.setUom(recipeBean.getUom());
        recipe.setDescription(recipeBean.getDescription());
        recipe.setFoodType(recipeBean.getFoodType());
        recipeRepository.save(recipe);
        log.debug("Created Recipe  with ID: {}", recipe.getId());
    }

    @Override
    public List<Recipe> findAllByName(String name) {
        return recipeRepository.findByNameContaining(name);
    }

    @Override
    public Page<Recipe> getRecipes(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return recipeRepository.findAll(pageRequest);
    }

}
