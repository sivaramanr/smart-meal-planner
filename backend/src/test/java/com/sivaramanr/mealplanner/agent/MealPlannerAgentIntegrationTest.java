package com.sivaramanr.mealplanner.agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.sessions.BaseSessionService;
import com.google.adk.sessions.InMemorySessionService;
import com.sivaramanr.mealplanner.MealPlannerApplication;
import com.sivaramanr.mealplanner.dao.FoodSessionDAO;
import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.service.FoodSessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MealPlannerApplication.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MealPlannerAgentIntegrationTest {

    @Autowired
    private MealPlannerAgent mealPlannerAgent;

    @Autowired
    private FoodSessionServiceImpl sessionService;

    @Autowired
    private FoodSessionDAO foodSessionDAO;

    private List<FoodSessionBean> foodSessionBeans;

    @Autowired
    private InMemorySessionService sessionServiceBase;

    @BeforeEach
    void setUp() {
        foodSessionBeans = new ArrayList<>();
        createSession("Breakfast", "Session in the morning", "08:00 AM - 10:00 AM");
        createSession("Lunch", "Session at noon", "12:00 PM - 02:00 PM");
        createSession("Dinner", "Session in the night", "07:00 PM - 09:00 PM");
    }

    @Test
    void testCreateAgent() {
        BaseAgent agent = mealPlannerAgent.createAgent();
        assertNotNull(agent);
        assertEquals(MealPlannerAgent.NAME, agent.name());
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