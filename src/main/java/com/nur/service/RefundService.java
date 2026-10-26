package com.nur.service;

import com.nur.model.RefundPolicy;
import com.nur.repository.RefundPolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundPolicyRepository repository;

    public RefundPolicy checkEligibility(String orderStatus) {
        log.info("REFUND SERVICE -> Checking status: {}", orderStatus);
        return repository.findByOrderStatus(orderStatus)
                .orElseThrow(() -> new RuntimeException("Refund policy not found for: " + orderStatus));
    }
}