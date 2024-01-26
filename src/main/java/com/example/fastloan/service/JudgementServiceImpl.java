package com.example.fastloan.service;

import com.example.fastloan.domain.Application;
import com.example.fastloan.domain.Judgement;
import com.example.fastloan.dto.ApplicationDTO;
import com.example.fastloan.dto.JudgementDTO;
import com.example.fastloan.dto.JudgementDTO.*;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.JudgementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class JudgementServiceImpl implements JudgementService {

    private final JudgementRepository judgementRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;
    @Override
    public Response create(Request request) {
        Long applicationId = request.getApplicationId();
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Judgement judgement = modelMapper.map(request, Judgement.class);
        Judgement saved = judgementRepository.save(judgement);
        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long judgementId) {
        Judgement judgement = judgementRepository.findById(judgementId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public Response getJudgementOfApplication(Long applicationId) {
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Judgement judgement = judgementRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public Response update(Long judgementId, Request request) {
        Judgement judgement = judgementRepository.findByApplicationId(judgementId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        judgement.setName(request.getName());
        judgement.setApprovalAmount(request.getApprovalAmount());
        judgementRepository.save(judgement);

        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public void delete(Long judgementId) {
        Judgement judgement = judgementRepository.findByApplicationId(judgementId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        judgement.setIsDeleted(true);
        judgementRepository.save(judgement);
    }

    @Override
    public ApplicationDTO.GrantAmount grant(Long judgementId) {
        Judgement judgement = judgementRepository.findByApplicationId(judgementId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Long applicationId = judgement.getApplicationId();
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        BigDecimal approvalAmount = judgement.getApprovalAmount();
        application.setApprovalAmount(approvalAmount);
        Application saved = applicationRepository.save(application);

        System.out.println(saved.getApprovalAmount().toString());

        return modelMapper.map(saved, ApplicationDTO.GrantAmount.class);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
