package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.service.NutritionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NutritionTool {

    @Autowired
    private NutritionService nutritionService;

    @Annotations.Schema(
            name = "get_nutritions",
            description = """
                    Retrieves a list of nutritions.
                    Nutritions are the substances in food that the body needs to function properly
                    Examples of nutritions are Carbohydrates, Proteins, Fats, Vitamins, Minerals, etc.
                    """)
    public Map<String, List<NutritionBean>> getNutritions(@Annotations.Schema(name = "toolContext") ToolContext context) {
        String toolName = context.functionCallId().orElse("get_nutritions");
        log.debug("Invoking tool '{}' in sessionId={}", toolName, context.sessionId());

        Page<NutritionBean> result = nutritionService.getNutritions(0, Integer.MAX_VALUE);

        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.getTotalElements());
        return Map.of("result", result.getContent());
    }

}
