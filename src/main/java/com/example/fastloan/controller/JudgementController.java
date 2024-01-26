package com.example.fastloan.controller;

import com.example.fastloan.dto.ApplicationDTO;
import com.example.fastloan.dto.JudgementDTO;
import com.example.fastloan.dto.JudgementDTO.*;
import com.example.fastloan.dto.ResponseDTO;
import com.example.fastloan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgements")
public class JudgementController extends AbstractController {
    private final JudgementService judgementService;

    @PostMapping
    public ResponseDTO<JudgementDTO.Response> create(@RequestBody JudgementDTO.Request request) {
        return ok(judgementService.create(request));
    }

    @GetMapping("/{judgementId}")
    public ResponseDTO<JudgementDTO.Response> getByJudgementId(@PathVariable("judgementId") Long judgementId) {
        return ok(judgementService.get(judgementId));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseDTO<JudgementDTO.Response> getByApplicationId(@PathVariable("applicationId") Long applicationId) {
        return ok(judgementService.getJudgementOfApplication(applicationId));
    }
    @PutMapping("/{judgementId}")
    public ResponseDTO<JudgementDTO.Response> update(
            @PathVariable("judgementId") Long judgementId,
            @RequestBody JudgementDTO.Request request) {
        return ok(judgementService.update(judgementId, request));
    }

    @DeleteMapping("/{judgementId}")
    public ResponseDTO<Void> delete(@PathVariable("judgementId") Long judgementId) {
        judgementService.delete(judgementId);
        return ok();
    }

    @PatchMapping("/{judgementId}/grants")
    public ResponseDTO<ApplicationDTO.GrantAmount> grant(@PathVariable("judgementId") Long judgementId) {
        return ok(judgementService.grant(judgementId));
    }
}
