package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.RecipeGroupDAO;
import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MealPlannerApplication.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RecipeGroupServiceImplIntegrationTest {

    @Autowired
    private RecipeGroupService recipeGroupService;

    @Autowired
    private RecipeGroupDAO recipeGroupDAO;

    private List<RecipeGroupBean> recipeGroupBeans;

    @BeforeEach
    void setUp() {
        recipeGroupBeans = new ArrayList<>();
        createRecipeGroup("Sambar", "Cereal gravy for white rice", "VEG");
        createRecipeGroup("Indian Bread", "Breads like Chapathi", "VEG");
        createRecipeGroup("Chicken Briyani", "Chicken Briyani", "NON_VEG");
    }

    @Test
    void testGetRecipeGroups() {
        Page<RecipeGroupBean> result = recipeGroupService.getRecipeGroups(0, 10);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        for (int i = 0; i < recipeGroupBeans.size(); i++) {
            testRecipeGroupBean(recipeGroupBeans.get(i), result.getContent().get(i));
        }
    }

    private void testRecipeGroupBean(RecipeGroupBean expected, RecipeGroupBean actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertFalse(expected.getFoodType().isEmpty());
    }

    private void createRecipeGroup(String name, String description, String foodType) {
        RecipeGroupBean recipeGroupBean = new RecipeGroupBean();
        recipeGroupBean.setName(name);
        recipeGroupBean.setDescription(description);
        recipeGroupBean.setFoodType(foodType);
        recipeGroupDAO.createRecipeGroup(recipeGroupBean);
        recipeGroupBeans.add(recipeGroupBean);
    }

}
