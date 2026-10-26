package com.nur.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface WorkflowSupportAgent {

    @Agent
    public String handle(@V("request") String request);

}
