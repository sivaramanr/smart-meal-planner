package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.dto.RecipeBean;
import com.sivaramanr.mealplanner.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SearchRecipeTool {

    @Autowired
    private RecipeService recipeService;

    @Annotations.Schema(
            name = "search_recipe",
            description = """
                    Searches for recipes by name.
                    if a recipe name is provided, it returns all recipes that contain the given name.
                    """)
    public Map<String, List<RecipeBean>> searchRecipes(
            @Annotations.Schema(
                    name = "recipeName",
                    description = "The name of the recipe to search for."
            )
            String recipeName,
            @Annotations.Schema(name = "toolContext") ToolContext context) {
        String toolName = context.functionCallId().orElse("search_recipe");
        log.debug("Invoking tool '{}' in sessionId={}, recipeName={}", toolName, context.sessionId(), recipeName);

        List<RecipeBean> result = recipeService.findAllByName(recipeName);

        log.debug("Tool '{}' completed in sessionId={} with result.size={}", toolName, context.sessionId(), result.size());
        return Map.of("result", result);
    }

}
