package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.NutritionBean;
import com.sivaramanr.mealplanner.service.IngredientNutritionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class IngredientNutritionTool {

    @Autowired
    private IngredientNutritionService ingredientNutritionService;

    @Annotations.Schema(
            name = "get_ingredient_nutritions",
            description = """
                    Retrieves a consolidated map of nutritions based on the provided ingredient quantities.
                    Examples of ingredient nutritions are {'id': '1', 'category':'Vitamin', 'name': 'Vitamin C', 'description': 'Vitamin', 'uom': 'mg', value='200'}.
                    """)
    public Map<String, List<NutritionBean>> getIngredientNutritions(
            @Annotations.Schema(
                    name = "ingredientsQuantityMap",
                    description = "A map where the key is the ingredient ID and the value is the quantity of that ingredient."
            )
            Map<String, String> ingredientsQuantityMap,
            @Annotations.Schema(name = "toolContext") ToolContext context
    ) {
        // Log tool invocation
        String toolName = context.functionCallId().orElse("get_ingredient_nutritions");
        log.debug("Invoking tool '{}' in sessionId={}", toolName, context.sessionId());

        List<NutritionBean> result = ingredientNutritionService.getIngredientNutritions(ingredientsQuantityMap);

        // Log tool completion
        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.size());
        return Map.of("result", result);
    }

}
