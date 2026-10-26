package com.nur.tool;

import com.nur.service.RefundService;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefundTool {

    private final RefundService refundService;

    @Tool("Check whether an order is eligible for a refund based on order status")
    public String checkRefundEligibility(String orderStatus) {
        log.info(">>> REFUND TOOL CALLED -> {}", orderStatus);
        return refundService.checkEligibility(orderStatus).toString();
    }
}
