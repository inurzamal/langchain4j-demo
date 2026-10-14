package com.nur.service;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface GeneralAIAgent {

    String chat(String message);
}