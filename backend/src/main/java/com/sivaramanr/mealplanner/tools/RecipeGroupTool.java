package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.dto.RecipeGroupBean;
import com.sivaramanr.mealplanner.service.RecipeGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RecipeGroupTool {

    @Autowired
    private RecipeGroupService recipeGroupService;

    @Annotations.Schema(
            name = "get_recipe_groups",
            description = """
                    Retrieves a list of recipe groups.
                    A recipe group is a categorization of recipes.
                    Examples of recipe groups are Indian Bread, Curry, Rice Dishes, Desserts, etc.
                    """)
    public Map<String, List<RecipeGroupBean>> getRecipeGroups(@Annotations.Schema(name = "toolContext") ToolContext context) {
        String toolName = context.functionCallId().orElse("get_recipe_groups");
        log.debug("Invoking tool '{}' in sessionId={}", toolName, context.sessionId());

        Page<RecipeGroupBean> result = recipeGroupService.getRecipeGroups(0, Integer.MAX_VALUE);

        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.getTotalElements());
        return Map.of("result", result.getContent());
    }

    public Map<String, List<RecipeGroupBean>> getLimitedRecipeGroups() {
        Page<RecipeGroupBean> result = recipeGroupService.getRecipeGroups(0, 5);
        return Map.of("result", result.getContent());
    }

}
