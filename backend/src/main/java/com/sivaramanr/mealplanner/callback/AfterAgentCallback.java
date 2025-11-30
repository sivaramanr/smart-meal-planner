package com.sivaramanr.mealplanner.callback;

import com.google.adk.agents.CallbackContext;
import com.google.adk.agents.Callbacks;
import com.google.genai.types.Content;
import com.sivaramanr.mealplanner.agent.AgentCacheUtils;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AfterAgentCallback implements Callbacks.AfterAgentCallback {

    @Override
    public Maybe<Content> call(CallbackContext callbackContext) {
        log.info("AfterAgentCallback invoked for agent: {}, sessionId: {}, invocationId: {}",
                callbackContext.agentName(), callbackContext.sessionId(), callbackContext.invocationId());
        AgentCacheUtils.clear(callbackContext.sessionId());
        return Maybe.empty();
    }

}
