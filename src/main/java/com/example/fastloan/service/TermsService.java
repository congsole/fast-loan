package com.example.fastloan.service;


import com.example.fastloan.dto.TermsDTO.*;
import java.util.List;

public interface TermsService {
    Response create(Request request);
    Response get(Long termsId);
    Response update(Long termsId, Request request);
    void delete(Long termsId);
    List<Response> getAll();
}
