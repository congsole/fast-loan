package com.example.fastloan.service;

import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy( // 지정된 경로로 파일을 카피한다.
                        file.getInputStream(),
                        Paths.get(uploadPath).resolve(file.getOriginalFilename()),
                        StandardCopyOption.REPLACE_EXISTING // 중복 파일 덮어쓰기
                        );
        } catch(Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri()); // 절대 경로를 통해서 파일을 받아온다.

            if(resource.isReadable() || resource.exists()) {
                return resource;
            } else {
                throw new BaseException(ResultType.NOT_EXIST);
            }
        } catch(Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Paths.get(uploadPath), 1).filter(path -> !path.equals(Paths.get(uploadPath)));
        } catch(Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }
}
