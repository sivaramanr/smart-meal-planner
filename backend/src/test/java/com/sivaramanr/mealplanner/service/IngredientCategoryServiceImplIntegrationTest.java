package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.IngredientCategoryDAO;
import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {MealPlannerApplication.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IngredientCategoryServiceImplIntegrationTest {

    @Autowired
    private IngredientCategoryService ingredientCategoryService;

    @Autowired
    private IngredientCategoryDAO ingredientCategoryDAO;

    private List<IngredientCategoryBean> ingredientCategoryBeans;

    @BeforeEach
    void setUp() {
        ingredientCategoryBeans = new ArrayList<>();
        createIngredientCategory("Provisions", "Provisions for cooking");
        createIngredientCategory("Vegetables", "Vegetables for cooking");
        createIngredientCategory("Meat", "Meat items for cooking");
    }

    @Test
    void testGetFoodSessions() {
        Page<IngredientCategoryBean> result = ingredientCategoryService.getIngredientCategories(0, 10);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        for (int i = 0; i < ingredientCategoryBeans.size(); i++) {
            testIngredientCategoryBean(ingredientCategoryBeans.get(i), result.getContent().get(i));
        }
    }

    private void testIngredientCategoryBean(IngredientCategoryBean expected, IngredientCategoryBean actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    private void createIngredientCategory(String name, String description) {
        IngredientCategoryBean ingredientCategoryBean = new IngredientCategoryBean();
        ingredientCategoryBean.setName(name);
        ingredientCategoryBean.setDescription(description);
        ingredientCategoryDAO.createIngredientCategory(ingredientCategoryBean);
        ingredientCategoryBeans.add(ingredientCategoryBean);
    }

}