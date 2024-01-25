package com.example.fastloan.controller;

import com.example.fastloan.dto.ApplicationDTO.*;
import com.example.fastloan.dto.FileDTO;
import com.example.fastloan.dto.ResponseDTO;
import com.example.fastloan.service.ApplicationService;
import com.example.fastloan.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {
    private final ApplicationService applicationService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDTO<Response> get(@PathVariable("applicationId") Long applicationId) {
        return ok(applicationService.get(applicationId));
    }

    @PutMapping("/{applicationId}")
    public ResponseDTO<Response> update(@PathVariable("applicationId") Long applicationId, @RequestBody Request request) {
        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseDTO<Response> delete(@PathVariable("applicationId") Long applicationId) {
        applicationService.delete(applicationId);
        return ok();
    }


    @PostMapping("/{applicationId}/terms")
    public ResponseDTO<Boolean> acceptTerms(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody AcceptTerms request) {
        return ok(applicationService.acceptTerms(applicationId, request));
    }

    @PostMapping("/{applicationId}/files")
    public ResponseDTO<Void> upload(@PathVariable("applicationId") Long applicationId, MultipartFile file) {
        fileStorageService.save(applicationId, file);
        return ok();
    }

    @GetMapping("/{applicationId}/files")                   // requestParam...경로 뒤에 쿼리 값
    public ResponseEntity<Resource> download(@PathVariable("applicationId") Long applicationId, @RequestParam(value="fileName") String fileName) {
        Resource file = fileStorageService.load(applicationId, fileName);
        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\""
                )
                .body(file);
    }
    @GetMapping("/{applicationId}/files/info")
    public ResponseDTO<List<FileDTO>> getFileInfos(@PathVariable("applicationId") Long applicationId) {
        List<FileDTO> fileInfoList = fileStorageService.loadAll(applicationId).map(path -> {
            String fileName = path.getFileName().toString();
            return FileDTO.builder()
                    .name(fileName)
                    .url(
                            MvcUriComponentsBuilder
                                    .fromMethodName(
                                            ApplicationController.class
                                            , "download"
                                            , fileName)
                                    .build()
                                    .toString()
                    )
                    .build();
        }).collect(Collectors.toList());
        return ok(fileInfoList);
    }

    @DeleteMapping("/{applicationId}/files")
    public ResponseDTO<Void> deleteAll(@PathVariable("applicationId") Long applicationId) {
        fileStorageService.deleteAll(applicationId);
        return ok();
    }
}
