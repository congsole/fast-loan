package com.example.fastloan.service;

import com.example.fastloan.domain.Terms;
import com.example.fastloan.dto.TermsDTO.*;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Terms terms = modelMapper.map(request, Terms.class);
        Terms saved = termsRepository.save(terms);
        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long termsId) {
        Terms terms = termsRepository.findById(termsId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(terms, Response.class);
    }

    @Override
    public Response update(Long termsId, Request request) {
        Terms terms = termsRepository.findById(termsId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        terms.setName(request.getName());
        terms.setTermsDetailUrl(request.getTermsDetailUrl());

        Terms updated = termsRepository.save(terms);

        return modelMapper.map(updated, Response.class);
    }

    @Override
    public void delete(Long termsId) {
        Terms terms = termsRepository.findById(termsId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        terms.setIsDeleted(true);
    }

    @Override
    public List<Response> getAll() {
        return termsRepository.findAll().stream().map(term -> modelMapper.map(term, Response.class)).collect(Collectors.toList());
    }
}
