package com.example.fastloan.service;


import com.example.fastloan.dto.ApplicationDTO.*;

public interface ApplicationService {
    Response create(Request request);

    Response get(Long applicationId);

    Response update(Long applicationId, Request request);

    void delete(Long applicationId);

    Boolean acceptTerms(Long applicationId, AcceptTerms request);

    Response contract(Long applicationId);
}
