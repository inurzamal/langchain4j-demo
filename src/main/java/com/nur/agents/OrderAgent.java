package com.nur.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Sub-agent responsible for fetching order details from the database.
 * <p>
 * The Supervisor delegates to this agent whenever the customer request
 * involves looking up, tracking, or cancelling an order.
 */
public interface OrderAgent {

    @UserMessage("""
            The customer request is:

            {{request}}

            Extract the order ID from the request.

            Use the available tool to get
            the real order details.

            Return the complete order details only.
            """)
    @Agent(
            outputKey = "orderDetails",
            description = "Fetches the current order details, status, and delivery information for a given order ID. "
                    + "Can also cancel an order if it has not been shipped yet."
    )
    String checkOrder(@V("request") String request);
}
