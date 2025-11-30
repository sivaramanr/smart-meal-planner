package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.IngredientCategoryBean;
import com.sivaramanr.mealplanner.service.IngredientCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class IngredientCategoryTool {

    @Autowired
    private IngredientCategoryService ingredientCategoryService;

    @Annotations.Schema(
            name = "get_ingredient_categories",
            description = """
                    Retrieves a list of ingredient categories.
                    Examples of ingredient categories are Provisions, Vegetables, Meat, Spices, etc.
                    """)
    public Map<String, List<IngredientCategoryBean>> getIngredientCategories(@Annotations.Schema(name = "toolContext") ToolContext context) {
        // Log tool invocation
        String toolName = context.functionCallId().orElse("get_ingredient_categories");
        log.debug("Invoking tool '{}' in sessionId={}", toolName, context.sessionId());

        Page<IngredientCategoryBean> result = ingredientCategoryService.getIngredientCategories(0, Integer.MAX_VALUE);

        // Log tool completion
        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.getTotalElements());
        return Map.of("result", result.getContent());
    }

}
