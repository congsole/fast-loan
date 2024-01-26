package com.example.fastloan.service;

import com.example.fastloan.domain.Application;
import com.example.fastloan.domain.Judgement;
import com.example.fastloan.domain.Terms;
import com.example.fastloan.dto.ApplicationDTO.*;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.AcceptTermsRepository;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.JudgementRepository;
import com.example.fastloan.repository.TermsRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final AcceptTermsRepository acceptTermsRepository;
    private final JudgementRepository judgementRepository;
    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(application, Response.class);
    }

    @Override
    public Response update(Long applicationId, Request request) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setName(request.getName());
        application.setCellPhone(request.getCellPhone());
        application.setEmail(request.getEmail());
        application.setHopeAmount(request.getHopeAmount());

        applicationRepository.save(application);

        return modelMapper.map(application, Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setIsDeleted(true);
        applicationRepository.save(application);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, AcceptTerms request) {
        applicationRepository.findById(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        List<Terms> termsList = termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"));
        if(termsList.isEmpty()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        List<Long> termsIdList = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());

        List<Long> acceptTermsIdList = request.getAcceptTermsId();
        Collections.sort(acceptTermsIdList);

        if(termsIdList.size() != acceptTermsIdList.size()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        if(!termsIdList.containsAll(acceptTermsIdList)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<com.example.fastloan.domain.AcceptTerms> savedAcceptTermsList
                = new ArrayList<>();
        for(Long acceptedTermsId : acceptTermsIdList) {
            com.example.fastloan.domain.AcceptTerms savedAcceptTerms =
            acceptTermsRepository.save(
                    com.example.fastloan.domain.AcceptTerms.builder()
                            .applicationId(applicationId)
                            .termsId(acceptedTermsId)
                            .build()
            );
            savedAcceptTermsList.add(savedAcceptTerms);
        }

        return savedAcceptTermsList.size() == acceptTermsIdList.size();
    }

    @Override
    public Response contract(Long applicationId) {
        // 신청 정보가 있는지 체크
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        // 신청한 정보에 대한 심사 정보가 있는지 체크
        Judgement judgement = judgementRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });;
        // 승인 금액 > 0 검증
        if(judgement.getApprovalAmount() == null || judgement.getApprovalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        //계약 체결
        application.setContractedAt(LocalDateTime.now());
        applicationRepository.save(application);

        return null;
    }
}
