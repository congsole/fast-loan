package com.example.fastloan.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CounselDTO implements Serializable {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Request {
        private String name;
        private String cellPhone;
        private String email;
        private String memo;
        private String address;
        private String addressDetail;
        private String zipCode;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long counselId;
        private String name;
        private String cellPhone;
        private String email;
        private String memo;
        private String address;
        private String addressDetail;
        private String zipCode;

        private LocalDateTime appliedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
