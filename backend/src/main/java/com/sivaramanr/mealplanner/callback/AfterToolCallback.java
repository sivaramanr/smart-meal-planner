package com.sivaramanr.mealplanner.callback;

import com.google.adk.agents.Callbacks;
import com.google.adk.agents.InvocationContext;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.ToolContext;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AfterToolCallback implements Callbacks.AfterToolCallback {

    @Override
    public Maybe<Map<String, Object>> call(InvocationContext invocationContext, BaseTool baseTool,
                                           Map<String, Object> input, ToolContext toolContext, Object response) {
        log.info("BeforeToolCallback invoked for agent: {}, sessionId: {}, invocationId: {}, tool: {}",
                invocationContext.appName(), invocationContext.session().id(),
                invocationContext.invocationId(), baseTool.name());
        return Maybe.empty();
    }

}
