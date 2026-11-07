package com.nur.config;

import com.nur.agents.OrderAgent;
import com.nur.agents.RefundAgent;
import com.nur.agents.WorkflowSupportAgent;
import com.nur.tool.OrderTools;
import com.nur.tool.RefundTool;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the AI agent system.
 * <p>
 * Wires up a <b>Supervisor</b> pattern where a top-level orchestrator agent
 * dynamically delegates to specialised sub-agents based on the customer's intent,
 * instead of running every agent in a fixed sequential pipeline.
 *
 * <pre>
 *                  ┌──────────────────────┐
 *                  │  WorkflowSupport     │
 *                  │  (Supervisor Agent)  │
 *                  └──────┬───────┬───────┘
 *            decides who  │       │  to call
 *          ┌──────────────┘       └──────────────┐
 *          ▼                                     ▼
 *   ┌─────────────┐                      ┌──────────────┐
 *   │ OrderAgent   │                      │ RefundAgent  │
 *   │ (+ tools)    │                      │ (+ tools)    │
 *   └─────────────┘                      └──────────────┘
 * </pre>
 */
@Configuration
public class AgentConfig {

    private static final String SUPERVISOR_CONTEXT = """
            You are a friendly and professional e-commerce customer support supervisor.

            Evaluate the customer's request carefully and coordinate with
            the specialised agents at your disposal:

            - Delegate to **OrderAgent** when you need to look up order details,
              check delivery status, or cancel an order.
            - Delegate to **RefundAgent** when the customer is asking about or
              requesting a refund. Note: RefundAgent needs order details to work,
              so make sure OrderAgent has been called first.

            After gathering the necessary information from the sub-agents,
            compose a concise, helpful, and empathetic final response for the customer.
            Do not invent any information — only use facts returned by the agents.
            """;

    private static final int MAX_AGENT_INVOCATIONS = 5;

    /**
     * Sub-agent that fetches order details from the database.
     */
    @Bean
    public OrderAgent orderAgent(ChatModel chatModel, OrderTools orderTools) {
        return AgenticServices
                .agentBuilder(OrderAgent.class)
                .chatModel(chatModel)
                .tools(orderTools)
                .outputKey("orderDetails")
                .build();
    }

    /**
     * Sub-agent that evaluates refund eligibility against company policy.
     */
    @Bean
    public RefundAgent refundAgent(ChatModel chatModel, RefundTool refundTool) {
        return AgenticServices
                .agentBuilder(RefundAgent.class)
                .chatModel(chatModel)
                .tools(refundTool)
                .outputKey("refundDecision")
                .build();
    }

    /**
     * Supervisor agent that dynamically orchestrates sub-agents based on
     * the customer's intent. Replaces the previous sequential workflow.
     */
    @Bean
    public WorkflowSupportAgent workflowSupportAgent(ChatModel chatModel,
                                                     OrderAgent orderAgent,
                                                     RefundAgent refundAgent) {
        return AgenticServices
                .supervisorBuilder(WorkflowSupportAgent.class)
                .chatModel(chatModel)
                .subAgents(orderAgent, refundAgent)
                .supervisorContext(SUPERVISOR_CONTEXT)
                .maxAgentsInvocations(MAX_AGENT_INVOCATIONS)
                .outputKey("finalResponse")
                .build();
    }
}
