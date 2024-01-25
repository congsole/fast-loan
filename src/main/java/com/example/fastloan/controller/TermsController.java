package com.example.fastloan.controller;


import com.example.fastloan.dto.ResponseDTO;
import com.example.fastloan.dto.TermsDTO.*;
import com.example.fastloan.repository.TermsRepository;
import com.example.fastloan.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController extends AbstractController {

    private final TermsService termsService;
    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(termsService.create(request));
    }

    @GetMapping("/{termsId}")
    public ResponseDTO<Response> get(@PathVariable("termsId") Long termsId) {
        return ok(termsService.get(termsId));
    }

    @GetMapping
    public ResponseDTO<List<Response>> getAll() {
        return ok(termsService.getAll());
    }

    @PutMapping("/{termsId}")
    public ResponseDTO<Response> update(@PathVariable("termsId") Long termsId, @RequestBody Request request) {
        return ok(termsService.update(termsId, request));
    }

    @DeleteMapping("/{termsId}")
    public ResponseDTO<Response> delete(@PathVariable("termsId") Long termsId) {
        termsService.delete(termsId);
        return ok();
    }


}
