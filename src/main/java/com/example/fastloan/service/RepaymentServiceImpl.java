package com.example.fastloan.service;

import com.example.fastloan.domain.Application;
import com.example.fastloan.domain.Entry;
import com.example.fastloan.domain.Repayment;
import com.example.fastloan.dto.BalanceDTO;
import com.example.fastloan.dto.RepaymentDTO;
import com.example.fastloan.dto.RepaymentDTO.*;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.EntryRepository;
import com.example.fastloan.repository.RepaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService{
    private final RepaymentRepository repaymentRepository;
    private final ApplicationRepository applicationRepository;
    private final EntryRepository entryRepository;
    private final BalanceService balanceService;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        if(!isRepayableApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Repayment repayment = modelMapper.map(request, Repayment.class);
        repayment.setApplicationId(applicationId);
        Repayment created = repaymentRepository.save(repayment);

        BalanceDTO.Response updatedBalance = balanceService.repaymentUpdate(
                applicationId,
                BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(request.getRepaymentAmount())
                        .type(BalanceDTO.RepaymentRequest.RepaymentType.REMOVE)
                        .build()
        );
        Response response = modelMapper.map(repayment, Response.class);
        response.setBalance(updatedBalance.getBalance());
        return response;
    }
    private Boolean isRepayableApplication(Long applicationId) {
        Optional<Application> existingApplication = applicationRepository.findById(applicationId);
        if(existingApplication.isEmpty()) {
            return false;
        }
        if(existingApplication.get().getContractedAt() == null) {
            return false;
        }
        Optional<Entry> existingEntry = entryRepository.findByApplicationId(applicationId);
        if(existingEntry.isEmpty()) {
            return false;
        }
        return true;
    }
}
