package com.nur.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RefundAgent {

    @UserMessage("""
            Customer request:

            {{request}}

            Order information:

            {{orderDetails}}

            Determine the current order status
            from the order information.

            Use the refund tool to check
            whether this order is eligible for refund.

            Return only the refund decision
            and policy explanation.
            """)
    @Agent(
            outputKey = "refundDecision",
            description = "Checks refund eligibility"
    )
    String checkRefund(@V("request") String request, @V("orderDetails") String orderDetails);


}