package com.example.fastloan.service;

import com.example.fastloan.dto.BalanceDTO;

public interface BalanceService {
    BalanceDTO.Response create(Long applicationId, BalanceDTO.Request request);

    BalanceDTO.Response update(Long applicationId, BalanceDTO.UpdateRequest request);
}
