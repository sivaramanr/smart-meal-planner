package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.NutritionDAO;
import com.sivaramanr.mealplanner.dto.NutritionBean;
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
class NutritionServiceImplIntegrationTest {

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private NutritionDAO nutritionDAO;

    private List<NutritionBean> recipeGroupBeans;

    @BeforeEach
    void setUp() {
        recipeGroupBeans = new ArrayList<>();
        createNutrition("Minerals", "Potassium", "Potassium is an essential mineral", "MG");
        createNutrition("Minerals", "Magnesium", "Magnesium is an essential mineral", "MG");
        createNutrition("Minerals", "Iron", "Iron is an essential mineral", "MG");
    }

    @Test
    void testGetNutritions() {
        Page<NutritionBean> result = nutritionService.getNutritions(0, 10);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        for (int i = 0; i < recipeGroupBeans.size(); i++) {
            testNutritionBean(recipeGroupBeans.get(i), result.getContent().get(i));
        }
    }

    private void testNutritionBean(NutritionBean expected, NutritionBean actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getUom(), actual.getUom());
    }

    private void createNutrition(String category, String name, String description, String uom) {
        NutritionBean nutritionBean = new NutritionBean();
        nutritionBean.setCategory(category);
        nutritionBean.setName(name);
        nutritionBean.setDescription(description);
        nutritionBean.setUom(uom);
        nutritionDAO.createNutrition(nutritionBean);
        recipeGroupBeans.add(nutritionBean);
    }

}
