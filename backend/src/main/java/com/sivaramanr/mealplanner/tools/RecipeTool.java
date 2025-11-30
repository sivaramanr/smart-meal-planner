package com.sivaramanr.mealplanner.tools;

import com.google.adk.tools.Annotations;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.agent.AgentCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class RecipeTool {

    @Value("${cookerp.baseURL}")
    private String cookERPBaseUrl;

    @Value("${cookerp.unitId}")
    private String cookERPUnitId;

    @Annotations.Schema(
            name = "get_recipes",
            description = """
                    Retrieves recipes. A recipe is a set of instructions that describes how to prepare or make something, especially a culinary dish.
                    Recipes include a list of ingredients required, the quantities of each ingredient, and step-by-step instructions on
                    how to combine and cook them to create the final dish.
                    Examples of recipe are "Spaghetti Bolognese", "Chicken Curry", "Vegetable Stir-fry", "Chocolate Cake", "Caesar Salad".
                    """)
    public Map<String, String> getRecipes(
            @Annotations.Schema(
                    name = "groupId",
                    description = "The unique identifier of the recipe group to filter recipes by group."
            )
            String groupId,
            @Annotations.Schema(name = "toolContext") ToolContext context
        ) {
        String toolName = context.functionCallId().orElse("get_recipes");
        log.debug("Invoking tool '{}' in sessionId={}, groupId={}", toolName, context.sessionId(), groupId);

        String authorization = (String) AgentCacheUtils.get(context.sessionId(), "authorization");

        if (StringUtils.isEmpty(authorization)) {
            log.debug("User is not authenticated. tool={}, sessionId={}", toolName, context.sessionId());
            return Map.of("status", "error", "description", "User is not authenticated. Please login to access recipe groups.");
        }

        RestClient restClient = RestClient.builder()
                .baseUrl(cookERPBaseUrl)
                .build();
        ResponseEntity<String> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipe")
                        .queryParam("page", "0")
                        .queryParam("size", "50")
                        .queryParam("groupId", groupId)
                        .build())
                .header("Content-Type", "application/json")
                .header("unitId", cookERPUnitId)
                .header("Authorization", authorization)
                .retrieve().toEntity(String.class);
        log.debug("Tool '{}' completed in sessionId={} with result", toolName, context.sessionId());
        return Map.of("result", Objects.requireNonNull(response.getBody()));
    }

}
