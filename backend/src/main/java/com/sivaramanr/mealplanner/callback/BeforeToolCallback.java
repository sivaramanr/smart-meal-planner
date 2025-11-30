package com.sivaramanr.mealplanner.callback;

import com.google.adk.agents.Callbacks;
import com.google.adk.agents.InvocationContext;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.ToolContext;
import com.sivaramanr.mealplanner.agent.AgentCacheUtils;
import com.sivaramanr.mealplanner.tools.RecipeGroupTool;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BeforeToolCallback implements Callbacks.BeforeToolCallback {

    @Autowired
    private RecipeGroupTool recipeGroupTool;

    @Override
    public Maybe<Map<String, Object>> call(InvocationContext invocationContext, BaseTool baseTool,
                                           Map<String, Object> input, ToolContext toolContext) {
        log.info("BeforeToolCallback invoked for agent: {}, sessionId: {}, invocationId: {}, tool: {}",
                invocationContext.appName(), invocationContext.session().id(),
                invocationContext.invocationId(), baseTool.name());

        String authorization = (String) AgentCacheUtils.get(invocationContext.session().id(), "authorization");

        if (baseTool.name().equals("get_recipe_groups") &&
                authorization == null) {
            log.warn("Authorization header is missing. Using limited recipe groups for get_recipe_groups tool.");
            Object result = recipeGroupTool.getLimitedRecipeGroups().get("result");
            return Maybe.just(Map.of("result", result, "description","Recipe group access is limited. To access all recipe groups, User should authenticate."));
        }
        return Maybe.empty();
    }

}
