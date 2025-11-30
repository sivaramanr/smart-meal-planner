package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.IngredientBean;
import com.sivaramanr.mealplanner.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class IngredientTool {

    @Autowired
    private IngredientService ingredientService;

    @Annotations.Schema(
            name = "get_ingredients",
            description = """
                    Retrieves ingredients used for recipes.
                    Ingredients are the raw materials used to prepare recipes.
                    Examples of ingredients are Carrot, Chicken, Rice, Salt, etc.
                    """)
    public Map<String, List<IngredientBean>> getIngredients(@Annotations.Schema(name = "toolContext") ToolContext context) {
        String toolName = context.functionCallId().orElse("get_ingredients");
        log.debug("Invoking tool '{}' in sessionId={}", toolName, context.sessionId());

        Page<IngredientBean> result = ingredientService.getIngredients(0, Integer.MAX_VALUE);

        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.getTotalElements());
        return Map.of("result", result.getContent());
    }

}
