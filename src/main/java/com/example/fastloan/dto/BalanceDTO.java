package com.example.fastloan.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BalanceDTO implements Serializable {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Request {
        private Long applicationId;
        private BigDecimal entryAmount;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class UpdateRequest {
        private Long applicationId;
        private BigDecimal beforeEntryAmount;
        private BigDecimal afterEntryAmount;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long balanceId;

        private Long applicationId;
        private BigDecimal entryAmount;
        private BigDecimal balanceAmount;

        private LocalDateTime appliedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
