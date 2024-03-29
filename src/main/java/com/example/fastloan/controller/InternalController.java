package com.example.fastloan.controller;


import com.example.fastloan.dto.EntryDTO.*;
import com.example.fastloan.dto.RepaymentDTO;
import com.example.fastloan.dto.ResponseDTO;
import com.example.fastloan.service.EntryService;
import com.example.fastloan.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.fastloan.dto.ResponseDTO.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/applications")
public class InternalController {
    private final EntryService entryService;
    private final RepaymentService repaymentService;
    @PostMapping("/{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable("applicationId") Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }
    @GetMapping("/{applicationId}/entries")
    public ResponseDTO<Response> get(@PathVariable("applicationId") Long applicationId) {
        return ok(entryService.get(applicationId));
    }
    @PutMapping("/entries/{entryId}")
    public ResponseDTO<UpdateResponse> update(@PathVariable("entryId") Long entryId, @RequestBody Request request) {
        return ok(entryService.update(entryId, request));
    }
    @DeleteMapping("/entries/{entryId}")
    public ResponseDTO<Void> delete(@PathVariable Long entryId) {
        entryService.delete(entryId);
        return ok();
    }

    @PostMapping("/{applicationId}/repayments")
    public ResponseDTO<RepaymentDTO.Response> create(@PathVariable Long applicationId, @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.create(applicationId, request));
    }

}

