package com.example.fastloan.service;

import com.example.fastloan.domain.Judgement;
import com.example.fastloan.dto.JudgementDTO;
import com.example.fastloan.dto.JudgementDTO.*;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.JudgementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
