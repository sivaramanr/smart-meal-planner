package com.sivaramanr.mealplanner.agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import com.sivaramanr.mealplanner.callback.*;
import com.sivaramanr.mealplanner.tools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MealPlannerAgent {

    public static final String NAME = "meal-planner-agent";

    static final String DESCRIPTION = "You are telling the food sessions and timings in a day.";

    static final String INSTRUCTION = """
        You are a helpful assistant to help users to plan their meals. 
        
        ** If the user asks abouts recipes or any dishes, Use the 'searchRecipes' tool which will give you a list of matching recipes. **
        ** If 'searchRecipes' returns no result, Use the 'getRecipeGroups' tool and get the recipe groups at first and then find the closest matching recipe group using your intelligence. **
        ** If you are unable to find the recipe group, Give the user the list of recipe groups that you received from 'getRecipeGroups' tool as a option to select. **
        ** If you found the matching recipe group or user selected any option from the list, Call 'getRecipes' tool using the 'id' associated with the matching recipe group **
        ** If 'getRecipes' tool returned no recipes, Politely tell the user that no recipes available in the respective group **
        ** If 'getRecipes' tool returned a list of recipes, Try to find the matching recipe by name from the list using your intelligence. **
        ** If you are unable to find any matching recipe from the result of 'getRecipes' tool, Ask user to choose one from the list **
        ** If you found the matching recipe or user selected any option from the list, Make sure the user has given the recipe quantity otherwise ask for it. **
        ** Once you have the recipe id and the required quantity, Call 'getRecipeIngredients' tool using the 'id' associated with the matching recipe and the quantity to get the ingredients for that recipe. **
        ** If you want to calculate the nutritional value for the recipe, Call 'getIngredientNutritions' tool using the ingredients received from 'getRecipeIngredients' tool along with their quantities. **
                
        Use the 'getSessions' tool to retrieve information about food sessions and their timings.
        Use the 'getRecipeGroups' tool to retrieve information about different recipe groups.
        Use the 'getIngredientCategories' tool to retrieve recipe's ingredient's categories.
        Use the 'getNutritions' tool to retrieve nutritions.
        Use the 'getIngredients' tool to retrieve ingredients, their category and price.
        Use the 'getRecipes' tool to retrieve supported recipes.
        Use the 'getRecipeIngredients' tool to retrieve ingredients for a recipe.
        Use the 'getIngredientNutritions' tool to retrieve nutritions for ingredients.
        """;

    static final String MODEL = "gemini-2.5-flash";

    @Value("${google.adk.reasoning-engine-name}")
    private String googleAdkReasoningEngineName;

    // Tools
    @Autowired
    private FoodSessionTool foodSessionTool;
    @Autowired
    private RecipeGroupTool recipeGroupTool;
    @Autowired
    private IngredientCategoryTool ingredientCategoryTool;
    @Autowired
    private NutritionTool nutritionTool;
    @Autowired
    private IngredientTool ingredientTool;
    @Autowired
    private RecipeTool recipeTool;
    @Autowired
    private SearchRecipeTool searchRecipeTool;
    @Autowired
    private RecipeIngredientsTool recipeIngredientsTool;
    @Autowired
    private IngredientNutritionTool ingredientNutritionTool;

    // Callbacks
    @Autowired
    private BeforeModelCallback beforeModelCallback;
    @Autowired
    private AfterModelCallback afterModelCallback;
    @Autowired
    private BeforeAgentCallback beforeAgentCallback;
    @Autowired
    private AfterAgentCallback afterAgentCallback;
    @Autowired
    private BeforeToolCallback beforeToolCallback;
    @Autowired
    private AfterToolCallback afterToolCallback;

    private static BaseAgent mealPlannerAgent;

    public synchronized BaseAgent createAgent() {
        if (mealPlannerAgent == null)
        {
            FunctionTool[] functionTools = {
                FunctionTool.create(foodSessionTool, "getFoodSessions"),
                FunctionTool.create(recipeGroupTool, "getRecipeGroups"),
                FunctionTool.create(ingredientCategoryTool, "getIngredientCategories"),
                FunctionTool.create(nutritionTool, "getNutritions"),
                FunctionTool.create(ingredientTool, "getIngredients"),
                FunctionTool.create(recipeTool, "getRecipes"),
                FunctionTool.create(searchRecipeTool, "searchRecipes"),
                FunctionTool.create(recipeIngredientsTool, "getRecipeIngredients"),
                FunctionTool.create(ingredientNutritionTool, "getIngredientNutritions")
            };

            mealPlannerAgent = LlmAgent.builder()
                .name(googleAdkReasoningEngineName)
                .description(DESCRIPTION)
                .instruction(INSTRUCTION)
                .model(MODEL)
                .beforeAgentCallback(beforeAgentCallback)
                .beforeModelCallback(beforeModelCallback)
                .afterModelCallback(afterModelCallback)
                .afterAgentCallback(afterAgentCallback)
                .beforeToolCallback(beforeToolCallback)
                .afterToolCallback(afterToolCallback)
                .tools(functionTools)
                .build();
        }
        return mealPlannerAgent;
    }

}
