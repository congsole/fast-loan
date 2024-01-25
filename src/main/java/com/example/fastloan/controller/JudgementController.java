package com.example.fastloan.controller;

import com.example.fastloan.dto.JudgementDTO;
import com.example.fastloan.dto.ResponseDTO;
import com.example.fastloan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgements")
public class JudgementController extends AbstractController {
    private final JudgementService judgementService;

    @PostMapping
    public ResponseDTO<JudgementDTO.Response> create(@RequestBody JudgementDTO.Request request) {
        return ok(judgementService.create(request));
    }
}
