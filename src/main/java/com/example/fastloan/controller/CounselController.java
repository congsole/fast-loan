package com.example.fastloan.controller;

import com.example.fastloan.dto.CounselDTO;
import com.example.fastloan.dto.CounselDTO.*;
import com.example.fastloan.dto.ResponseDTO;
import com.example.fastloan.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counsels")
public class CounselController extends AbstractController {

    private final CounselService counselService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(counselService.create(request));
    }

    @GetMapping("/{counselId}")
    public ResponseDTO<Response> get(@PathVariable("counselId") Long counselId) {
        return ok(counselService.get(counselId));
    }

    @PutMapping("/{counselId}")
    public ResponseDTO<Response> update(@PathVariable("counselId") Long counselId, @RequestBody Request request) {
        return ok(counselService.update(counselId, request));
    }

    @DeleteMapping("/{counselId}")
    public ResponseDTO<Response> delete(@PathVariable("counselId") Long counselId) {
        counselService.delete(counselId);
        return ok();
    }
}
