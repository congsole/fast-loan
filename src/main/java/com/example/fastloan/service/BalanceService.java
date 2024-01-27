package com.example.fastloan.service;

import com.example.fastloan.domain.Repayment;
import com.example.fastloan.dto.BalanceDTO;
import com.example.fastloan.dto.BalanceDTO.*;

public interface BalanceService {
    Response create(Long applicationId, Request request);

    Response update(Long applicationId, UpdateRequest request);
    Response repaymentUpdate(Long applicationId, RepaymentRequest request);
}
