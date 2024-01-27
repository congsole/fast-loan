package com.example.fastloan.service;

import com.example.fastloan.dto.RepaymentDTO.*;
public interface RepaymentService {
    Response create(Long applicationId, Request request);
}
