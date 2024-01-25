package com.example.fastloan.service;

import com.example.fastloan.dto.CounselDTO.*;


public interface CounselService {

    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);

    void delete(Long counselId);
}
