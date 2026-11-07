package com.nur.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Sub-agent responsible for evaluating refund eligibility.
 * <p>
 * The Supervisor delegates to this agent after order details have been
 * retrieved, when the customer is inquiring about or requesting a refund.
 */
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
            description = "Evaluates whether an order is eligible for a refund based on its current status "
                    + "and the company refund policy. Requires order details to be available."
    )
    String checkRefund(@V("request") String request, @V("orderDetails") String orderDetails);
}
