package com.example.fastloan.service;

import com.example.fastloan.dto.EntryDTO;
import com.example.fastloan.dto.EntryDTO.*;

public interface EntryService {
    Response create(Long applicationId, Request request);
    Response get(Long applicationId);

    UpdateResponse update(Long applicationId, Request request);

    void delete(Long entryId);
}
