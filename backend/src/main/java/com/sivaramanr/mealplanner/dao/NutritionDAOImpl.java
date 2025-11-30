package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.entity.Nutrition;
import com.sivaramanr.mealplanner.repository.NutritionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class NutritionDAOImpl implements NutritionDAO {

    @Autowired
    private NutritionRepository nutritionRepository;

    @Override
    public void createNutrition(NutritionBean nutritionBean) {
        final Nutrition nutrition = new Nutrition();
        nutrition.setId(ofNullable(nutritionBean.getId()).orElseGet(UUID.randomUUID()::toString));
        nutrition.setCategory(nutritionBean.getCategory());
        nutrition.setName(nutritionBean.getName());
        nutrition.setDescription(nutritionBean.getDescription());
        nutrition.setUom(nutritionBean.getUom());
        nutritionRepository.save(nutrition);
    }

    @Override
    public Page<Nutrition> getNutritions(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return nutritionRepository.findAll(pageRequest);
    }

}
