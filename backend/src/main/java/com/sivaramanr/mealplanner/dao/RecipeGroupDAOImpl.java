package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import com.sivaramanr.mealplanner.entity.RecipeGroup;
import com.sivaramanr.mealplanner.repository.RecipeGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class RecipeGroupDAOImpl implements RecipeGroupDAO {

    @Autowired
    private RecipeGroupRepository recipeGroupRepository;

    @Override
    public void createRecipeGroup(RecipeGroupBean recipeGroupBean) {
        RecipeGroup recipeGroup = new RecipeGroup();
        recipeGroup.setId(ofNullable(recipeGroupBean.getId()).orElseGet(UUID.randomUUID()::toString));
        recipeGroup.setName(recipeGroupBean.getName());
        recipeGroup.setDescription(recipeGroupBean.getDescription());
        recipeGroup.setFoodType(recipeGroupBean.getFoodType());
        recipeGroupRepository.save(recipeGroup);
        log.debug("Created Recipe Group with ID: {}", recipeGroup.getId());
    }

    @Override
    public Page<RecipeGroup> getRecipeGroups(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return recipeGroupRepository.findAll(pageRequest);
    }
}
