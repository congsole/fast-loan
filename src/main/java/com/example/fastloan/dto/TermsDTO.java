package com.example.fastloan.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class TermsDTO implements Serializable {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Request {
        private String name;
        private String termsDetailUrl;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long termsId;
        private String name;
        private String termsDetailUrl;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
