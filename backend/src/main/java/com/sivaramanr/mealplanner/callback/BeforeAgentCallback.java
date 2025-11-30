package com.sivaramanr.mealplanner.callback;

import com.google.adk.agents.CallbackContext;
import com.google.adk.agents.Callbacks;
import com.google.genai.types.Content;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeforeAgentCallback implements Callbacks.BeforeAgentCallback {

    @Override
    public Maybe<Content> call(CallbackContext callbackContext) {
        log.info("BeforeAgentCallback invoked for agent: {}, sessionId: {}, invocationId: {}",
                callbackContext.agentName(), callbackContext.sessionId(), callbackContext.invocationId());
        return Maybe.empty();
    }

}
