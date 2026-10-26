package com.nur.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ResponseAgent {

    @UserMessage("""
            Original customer request:

            {{request}}

            Order details:

            {{orderDetails}}

            Refund decision:

            {{refundDecision}}

            Create a short, clear and helpful
            response for the customer.

            Do not invent any information.
            """)
    @Agent(
            outputKey = "finalResponse",
            description = "Creates final customer response"
    )
    String createResponse(
            @V("request") String request,
            @V("orderDetails") String orderDetails,
            @V("refundDecision") String refundDecision
    );
}
