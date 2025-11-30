package com.sivaramanr.mealplanner.callback;

import com.google.adk.agents.CallbackContext;
import com.google.adk.agents.Callbacks;
import com.google.adk.models.LlmResponse;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@Component
@Slf4j
public class AfterModelCallback implements Callbacks.AfterModelCallback {

    private List<String> prohibitedKeywords = List.of(
        "get_sessions", "get_recipe_groups", "get_ingredient_categories",
        "get_nutritions", "get_ingredients", "get_recipes", "get_recipe_ingredients"
    );

    @Override
    public Maybe<LlmResponse> call(CallbackContext callbackContext, LlmResponse llmResponse) {
        log.info("AfterModelCallback invoked for agent: {}, sessionId: {}, invocationId: {}",
                callbackContext.agentName(), callbackContext.sessionId(), callbackContext.invocationId());
        final AtomicBoolean prohibitedFound = new AtomicBoolean(false);
        llmResponse.content().flatMap(Content::parts).ifPresent(parts -> {
            parts
            .stream()
            .map(Part::text)
            .filter(Predicate.not(Objects::isNull))
            .filter(this::containsProhibitedKeyword)
            .findFirst()
            .map(Optional::isPresent)
            .ifPresent(prohibitedFound::set);
        });
        if (prohibitedFound.get())
        {
            log.warn("Response contains prohibited keywords, agent: {}, sessionId: {}, invocationId: {}",
                    callbackContext.agentName(), callbackContext.sessionId(), callbackContext.invocationId());
            return Maybe.just(LlmResponse
                .builder()
                .content(Content
                    .builder()
                    .parts(List.of(com.google.genai.types.Part.builder()
                        .text("I cannot provide the requested information because it contains restricted content.")
                        .build()))
                    .build())
                .build());
        }

        return Maybe.empty();
    }

    private boolean containsProhibitedKeyword(Optional<String> textOptional) {
        if (textOptional.isEmpty()) {
            return false;
        }
        String text = textOptional.get().toLowerCase();
        for (String keyword : prohibitedKeywords) {
            if (text.contains(keyword)) {
                log.warn("Prohibited keyword detected: {}", keyword);
                return true;
            }
        }
        return false;
    }

}
