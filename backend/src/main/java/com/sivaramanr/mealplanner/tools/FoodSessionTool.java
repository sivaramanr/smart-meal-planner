package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.service.FoodSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FoodSessionTool {

    @Autowired
    private FoodSessionService foodSessionService;

    @Annotations.Schema(
            name = "get_sessions",
            description = """
                    Retrieves a list of sessions.
                    A foodSession is a meal time period in a day.
                    Examples of sessions are Breakfast, Lunch, Dinner, Snack, etc.
                    """)
    public Map<String, List<FoodSessionBean>> getFoodSessions(@Annotations.Schema(name = "toolContext") ToolContext context) {
        String toolName = context.functionCallId().orElse("get_sessions");
        log.debug("Invoking tool '{}' in sessionId={}", toolName, context.sessionId());

        Page<FoodSessionBean> result = foodSessionService.getFoodSessions(0, Integer.MAX_VALUE);

        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.getTotalElements());
        return Map.of("result", result.getContent());
    }

}
