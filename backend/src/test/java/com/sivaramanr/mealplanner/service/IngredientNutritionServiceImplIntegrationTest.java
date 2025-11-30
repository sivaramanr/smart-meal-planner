package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.IngredientDAO;
import com.sivaramanr.mealplanner.dao.IngredientNutritionDAO;
import com.sivaramanr.mealplanner.dao.NutritionDAO;
import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.dto.IngredientNutritionBean;
import com.sivaramanr.mealplanner.dto.NutritionBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {MealPlannerApplication.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IngredientNutritionServiceImplIntegrationTest {

    @Autowired
    private IngredientNutritionServiceImpl ingredientNutritionService;

    @Autowired
    private IngredientNutritionDAO ingredientNutritionDAO;

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private NutritionDAO nutritionDAO;

    private IngredientBean ingredient;

    private NutritionBean nutrition;

    @BeforeEach
    void setUp() {
        ingredient = new IngredientBean();
        ingredient.setId("ing1");
        ingredient.setName("Tomato");
        ingredientDAO.createIngredient(ingredient);

        nutrition = new NutritionBean();
        nutrition.setId("nut1");
        nutrition.setName("Vitamin C");

        IngredientNutritionBean ingredientNutrition = new IngredientNutritionBean();
        ingredientNutrition.setIngredient(ingredient);
        ingredientNutrition.setNutrition(nutrition);
        ingredientNutrition.setValue(20.0f); // per 100g

        ingredientNutritionDAO.createIngredientNutrition(ingredientNutrition);
    }

    @Test
    void testGetIngredientNutritions() {
        Map<String, String> input = new HashMap<>();
        input.put("ing1", "50.0f"); // 50g

        List<NutritionBean> result = ingredientNutritionService.getIngredientNutritions(input);

        assertNotNull(result);
    }
}