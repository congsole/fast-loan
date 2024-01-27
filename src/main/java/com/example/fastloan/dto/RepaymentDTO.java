package com.example.fastloan.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepaymentDTO implements Serializable {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Request {
        private BigDecimal repaymentAmount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long repaymentId;

        private Long applicationId;
        private BigDecimal repaymentAmount;
        private BigDecimal balance;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
