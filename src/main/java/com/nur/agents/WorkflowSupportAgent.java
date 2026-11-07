package com.nur.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

/**
 * Top-level Supervisor agent that orchestrates customer support requests.
 * <p>
 * Unlike a sequential workflow, the Supervisor dynamically decides which
 * sub-agents to invoke based on the customer's intent. It uses the Gemini
 * model to reason about the request and coordinate the specialized agents.
 */
public interface WorkflowSupportAgent {

    @Agent
    String handle(@V("request") String request);
}
