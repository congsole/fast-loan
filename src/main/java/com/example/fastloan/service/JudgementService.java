package com.example.fastloan.service;

import com.example.fastloan.dto.JudgementDTO.*;
import com.example.fastloan.dto.ApplicationDTO.GrantAmount;
public interface JudgementService {
    Response create(Request request);

    Response get(Long judgementId);

    Response getJudgementOfApplication(Long applicationId);

    Response update(Long judgementId, Request request);

    void delete(Long judgementId);

    GrantAmount grant(Long judgementId);
}
