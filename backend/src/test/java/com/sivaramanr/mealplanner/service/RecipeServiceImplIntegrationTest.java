package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.RecipeDAO;
import com.sivaramanr.mealplanner.dto.RecipeBean;
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
class RecipeServiceImplIntegrationTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeDAO recipeDAO;

    private List<RecipeBean> recipeBeans;

    @BeforeEach
    void setUp() {
        recipeBeans = new ArrayList<>();
        createRecipe("Sambar", "Cereal gravy for white rice", "VEG");
        createRecipe("Indian Bread", "Breads like Chapathi", "VEG");
        createRecipe("Chicken Briyani", "Chicken Briyani", "NON_VEG");
    }

    @Test
    void testGetRecipes() {
        Page<RecipeBean> result = recipeService.getRecipes(0, 10);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        for (int i = 0; i < recipeBeans.size(); i++) {
            testRecipeBean(recipeBeans.get(i), result.getContent().get(i));
        }
    }

    private void testRecipeBean(RecipeBean expected, RecipeBean actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertFalse(expected.getFoodType().isEmpty());
    }

    private void createRecipe(String name, String description, String foodType) {
        RecipeBean recipeBean = new RecipeBean();
        recipeBean.setName(name);
        recipeBean.setGroupId("default-group-id");
        recipeBean.setPortion(1f);
        recipeBean.setUom("Kg");
        recipeBean.setDescription(description);
        recipeBean.setFoodType(foodType);
        recipeDAO.createRecipe(recipeBean);
        recipeBeans.add(recipeBean);
    }

}
