package com.sivaramanr.mealplanner.tools;

import com.google.adk.sessions.BaseSessionService;
import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.agent.AgentCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class RecipeIngredientsTool {

    @Value("${cookerp.baseURL}")
    private String cookERPBaseUrl;

    @Value("${cookerp.unitId}")
    private String cookERPUnitId;

    @Autowired
    private BaseSessionService sessionService;

    @Annotations.Schema(
            name = "get_recipe_ingredients",
            description = """
                    Retrieves the ingredients for a given recipe. The response includes recipe and ingredient details 
                    such as ingredient names, quantities, and units of measurement.
                    Examples of ingredients are "Sugar", "Flour", "Butter", etc.
                    """)
    public Map<String, String> getRecipeIngredients(
            @Annotations.Schema(
                    name = "recipeId",
                    description = "The unique identifier of the recipe for which to retrieve ingredients."
            )
            String recipeId,
            @Annotations.Schema(
                    name = "quantity",
                    description = "The quantity of the recipe to calculate ingredients for. The value must be a valid number. i.e 10, 45, 400"
            )
            String quantity,
            @Annotations.Schema(name = "toolContext") ToolContext context) {
        String toolName = context.functionCallId().orElse("get_recipe_ingredients");
        log.debug("Invoking tool '{}' in sessionId={}, recipeId={}, quantity={}", toolName, context.sessionId(), recipeId, quantity);

        final StringBuilder quantityBuilder = new StringBuilder();
        if (StringUtils.isEmpty(quantity))
        {
            quantityBuilder.append("1");
        } else {
            quantityBuilder.append(quantity);
        }
        RestClient restClient = RestClient.builder()
                .baseUrl(cookERPBaseUrl)
                .build();

        String authorization = (String) AgentCacheUtils.get(context.sessionId(), "authorization");

        if (StringUtils.isEmpty(authorization)) {
            return Map.of("status", "error", "description", "User is not authenticated. Please login to access recipe ingredients.");
        }

        ResponseEntity<String> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipe/"+recipeId+"/ingredient")
                        .queryParam("quantity", quantityBuilder.toString())
                        .build())
                .header("Content-Type", "application/json")
                .header("unitId", cookERPUnitId)
                .header("Authorization", authorization)
                .retrieve().toEntity(String.class);

        log.debug("Tool '{}' completed in sessionId={} with result", toolName, context.sessionId());
        return Map.of("result", Objects.requireNonNull(response.getBody()));
    }

}
