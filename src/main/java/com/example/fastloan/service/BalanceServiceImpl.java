package com.example.fastloan.service;

import com.example.fastloan.domain.Balance;
import com.example.fastloan.dto.BalanceDTO;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.BalanceRepository;
import com.example.fastloan.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService{

    private final BalanceRepository balanceRepository;
    private final ModelMapper modelMapper;
    @Override
    public BalanceDTO.Response create(Long applicationId, BalanceDTO.Request request) {


        Balance balance = modelMapper.map(request, Balance.class);
        balance.setApplicationId(applicationId);
        balance.setBalance(request.getEntryAmount());

        balanceRepository.findByApplicationId(applicationId).ifPresent(b -> {
            balance.setBalanceId(b.getBalanceId());
            balance.setIsDeleted(b.getIsDeleted());
            balance.setCreatedAt(b.getCreatedAt());
            balance.setUpdatedAt(b.getUpdatedAt());
        });

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, BalanceDTO.Response.class);
    }

    @Override
    public BalanceDTO.Response update(Long applicationId, BalanceDTO.UpdateRequest request) {
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
        BigDecimal afterEntryAmount = request.getAfterEntryAmount();
        BigDecimal updatedBalance = balance.getBalance().subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.setBalance(updatedBalance);
        Balance updated = balanceRepository.save(balance);
        return modelMapper.map(updated, BalanceDTO.Response.class);
    }

    @Override
    public BalanceDTO.Response repaymentUpdate(Long applicationId, BalanceDTO.RepaymentRequest request) {
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        BigDecimal beforeBalance = balance.getBalance();
        BigDecimal updatedBalance;
        BigDecimal repaymentAmount = request.getRepaymentAmount();
        if(request.getType().equals(BalanceDTO.RepaymentRequest.RepaymentType.ADD)) {
            updatedBalance = beforeBalance.add(repaymentAmount);
        } else {
            updatedBalance = beforeBalance.subtract(repaymentAmount);
        }
        balance.setBalance(updatedBalance);
        Balance updated = balanceRepository.save(balance);
        return modelMapper.map(updated, BalanceDTO.Response.class);
    }
}
