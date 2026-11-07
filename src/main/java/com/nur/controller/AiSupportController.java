package com.nur.controller;

import com.nur.agents.WorkflowSupportAgent;
import com.nur.service.GeneralAIAgent;
import com.nur.service.OrderSupportAIAgent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai-support")
@RequiredArgsConstructor
@Tag(name = "AI Support", description = "Customer support endpoints powered by LangChain4j and Google Gemini")
public class AiSupportController {

    private final GeneralAIAgent agent;
    private final OrderSupportAIAgent orderSupportAIAgent;
    private final WorkflowSupportAgent workflowSupportAgent;

    @GetMapping
    @Operation(summary = "Chat with the AI agent",
            description = "General-purpose chat powered by Gemini with no tools attached.")
    public String chat(@RequestParam String message) {
        return agent.chat(message);
    }

    @GetMapping("/order")
    @Operation(summary = "Get order support",
            description = "Single agent with order database tools for tracking and cancellation.")
    public String orderSupport(@RequestParam String message) {
        return orderSupportAIAgent.chat(message);
    }

    @GetMapping("/workflow")
    @Operation(summary = "Supervisor-based customer support workflow",
            description = "A Supervisor agent dynamically delegates to OrderAgent and RefundAgent "
                    + "based on the customer's intent, then composes the final response.")
    public String workflowSupport(@RequestParam String request) {
        return workflowSupportAgent.handle(request);
    }
}
