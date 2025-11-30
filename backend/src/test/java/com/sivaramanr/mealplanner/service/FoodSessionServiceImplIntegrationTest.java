package com.sivaramanr.mealplanner.service;

import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.FoodSessionDAO;
import com.sivaramanr.mealplanner.dto.FoodSessionBean;
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
class FoodSessionServiceImplIntegrationTest {

    @Autowired
    private FoodSessionService sessionService;

    @Autowired
    private FoodSessionDAO foodSessionDAO;

    private List<FoodSessionBean> foodSessionBeans;

    @BeforeEach
    void setUp() {
        foodSessionBeans = new ArrayList<>();
        createSession("Breakfast", "Session in the morning", "08:00 AM - 10:00 AM");
        createSession("Lunch", "Session at noon", "12:00 PM - 02:00 PM");
        createSession("Dinner", "Session in the night", "07:00 PM - 09:00 PM");
    }

    @Test
    void testGetFoodSessions() {
        Page<FoodSessionBean> result = sessionService.getFoodSessions(0, 10);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        for (int i = 0; i < foodSessionBeans.size(); i++) {
            testSessionBean(foodSessionBeans.get(i), result.getContent().get(i));
        }
    }

    private void testSessionBean(FoodSessionBean expected, FoodSessionBean actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getTiming(), actual.getTiming());
    }

    private void createSession(String name, String description, String timing) {
        FoodSessionBean session = new FoodSessionBean();
        session.setName(name);
        session.setDescription(description);
        session.setTiming(timing);
        foodSessionDAO.createSession(session);
        foodSessionBeans.add(session);
    }

}