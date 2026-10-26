package com.nur.config;

import com.nur.agents.OrderAgent;
import com.nur.agents.RefundAgent;
import com.nur.agents.ResponseAgent;
import com.nur.agents.WorkflowSupportAgent;
import com.nur.tool.OrderTools;
import com.nur.tool.RefundTool;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public OrderAgent orderAgent(ChatModel chatModel, OrderTools orderTools) {

        return AgenticServices
                .agentBuilder(OrderAgent.class)
                .chatModel(chatModel)
                .tools(orderTools)
                .outputKey("orderDetails")
                .build();
    }

    @Bean
    public RefundAgent refundAgent(ChatModel chatModel, RefundTool refundTool) {

        return AgenticServices
                .agentBuilder(RefundAgent.class)
                .chatModel(chatModel)
                .tools(refundTool)
                .outputKey("refundDecision")
                .build();
    }

    @Bean
    public ResponseAgent responseAgent(ChatModel chatModel) {

        return AgenticServices
                .agentBuilder(ResponseAgent.class)
                .chatModel(chatModel)
                .outputKey("finalResponse")
                .build();
    }

    @Bean
    public WorkflowSupportAgent workflowSupportAgent(OrderAgent orderAgent,
                                                     RefundAgent refundAgent,
                                                     ResponseAgent responseAgent) {
        return AgenticServices
                .sequenceBuilder(WorkflowSupportAgent.class)
                .subAgents(orderAgent, refundAgent, responseAgent)
                .outputKey("finalResponse")
                .build();

    }
}
