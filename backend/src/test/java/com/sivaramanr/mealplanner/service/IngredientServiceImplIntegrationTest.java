package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.IngredientCategoryDAO;
import com.sivaramanr.mealplanner.dao.IngredientDAO;
import com.sivaramanr.mealplanner.dto.IngredientBean;
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
class IngredientServiceImplIntegrationTest {

    @Autowired
    private IngredientService nutritionService;

    @Autowired
    private IngredientCategoryDAO ingredientCategoryDAO;

    @Autowired
    private IngredientDAO nutritionDAO;

    private List<IngredientBean> ingredientBeans;

    @BeforeEach
    void setUp() {
        createIngredientCategory("1", "Provisions", "Provisions for cooking");
        createIngredientCategory("2", "Vegetables", "Vegetables for cooking");
        createIngredientCategory("3", "Meat", "Meat items for cooking");
        ingredientBeans = new ArrayList<>();
        createIngredient("1", "2", "Beans", "Beans", 80.0f);
        createIngredient("2", "2", "Onion", "Onion", 23.0f);
    }

    @Test
    void testGetIngredients() {
        Page<IngredientBean> result = nutritionService.getIngredients(0, 10);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        for (int i = 0; i < ingredientBeans.size(); i++) {
            testIngredientBean(ingredientBeans.get(i), result.getContent().get(i));
        }
    }

    @Test
    void testGetIngredient() {
        IngredientBean result = nutritionService.getIngredient("1");
        assertNotNull(result);
    }

    private void testIngredientBean(IngredientBean expected, IngredientBean actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    private void createIngredientCategory(String id, String name, String description) {
        IngredientCategoryBean ingredientCategoryBean = new IngredientCategoryBean();
        ingredientCategoryBean.setId(id);
        ingredientCategoryBean.setName(name);
        ingredientCategoryBean.setDescription(description);
        ingredientCategoryDAO.createIngredientCategory(ingredientCategoryBean);
    }

    private void createIngredient(String id, String categoryId, String name, String description, Float price) {
        IngredientBean ingredientBean = new IngredientBean();
        ingredientBean.setId(id);
        ingredientBean.setCategoryId(categoryId);
        ingredientBean.setName(name);
        ingredientBean.setDescription(description);
        ingredientBean.setPrice(price);
        nutritionDAO.createIngredient(ingredientBean);
        ingredientBeans.add(ingredientBean);
    }

}
