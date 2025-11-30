package com.sivaramanr.mealplanner.callback;

import com.google.adk.agents.CallbackContext;
import com.google.adk.agents.Callbacks;
import com.google.adk.models.LlmRequest;
import com.google.adk.models.LlmResponse;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BeforeModelCallback implements Callbacks.BeforeModelCallback {

    private List<String> prohibitedKeywords = List.of(
        "alcohol", "drug", "drugs", "tobacco", "cigarette", "cigarettes", "marijuana"
    );

    @Override
    public Maybe<LlmResponse> call(CallbackContext callbackContext, LlmRequest.Builder llmRequestBuilder) {

        log.info("BeforeModelCallback invoked for agent: {}, sessionId: {}, invocationId: {}",
                callbackContext.agentName(), callbackContext.sessionId(), callbackContext.invocationId());

        StringBuilder prohibited = new StringBuilder();
        LlmRequest llmRequest = llmRequestBuilder.build();

        llmRequest.contents().forEach(content -> {
            String text = content.text().toLowerCase();
            for (String keyword : prohibitedKeywords) {
                if (text.contains(keyword)) {
                    log.warn("Prohibited keyword detected: {}", keyword);
                    prohibited.append(keyword).append(", ");
                }
            }
        });
        if (!prohibited.isEmpty()) {
            log.warn("Request contains prohibited keywords: {}, agent: {}, sessionId: {}, invocationId: {}", prohibited,
                    callbackContext.agentName(), callbackContext.sessionId(), callbackContext.invocationId());
            final Content content = Content
                    .builder()
                    .parts(List.of(Part.builder()
                            .text("I cannot process this request because it contains the blocked keyword")
                            .build()))
                    .build();
            return new Maybe<LlmResponse>() {
                @Override
                protected void subscribeActual(@NonNull MaybeObserver<? super LlmResponse> observer) {
                    observer.onSuccess(LlmResponse
                            .builder()
                            .content(content)
                            .build());
                }
            };
        }
        return Maybe.empty();
    }

}
