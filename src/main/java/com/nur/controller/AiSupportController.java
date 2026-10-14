package com.nur.controller;

import com.nur.service.GeneralAIAgent;
import com.nur.service.OrderSupportAIAgent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai-support")
@RequiredArgsConstructor
public class AiSupportController {

    private final GeneralAIAgent agent;

    private final OrderSupportAIAgent orderSupportAIAgent;


    @GetMapping
    @Operation(summary = "Chat with the AI agent")
    public String chat(
            @RequestParam String message) {
        return agent.chat(message);
    }

    @GetMapping("/order")
    @Operation(summary = "Get order support")
    public String orderSupport(
            @RequestParam String message) {
        return orderSupportAIAgent.chat(message);
    }

}
