package com.example.fastloan.service;

import com.example.fastloan.domain.Application;
import com.example.fastloan.domain.Entry;
import com.example.fastloan.dto.BalanceDTO;
import com.example.fastloan.dto.EntryDTO;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final BalanceService balanceService;
    private final EntryRepository entryRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;
    @Override
    public EntryDTO.Response create(Long applicationId, EntryDTO.Request request) {
        // 계약 체결 여부 검토
        if(!isContractApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Entry entry = modelMapper.map(request, Entry.class);
        entry.setApplicationId(applicationId);

        Entry saved = entryRepository.save(entry);

        balanceService.create(
                applicationId,
                BalanceDTO.Request.builder()
                        .entryAmount(request.getEntryAmount())
                        .build()
        );
        return modelMapper.map(saved, EntryDTO.Response.class);
    }

    @Override
    public EntryDTO.Response get(Long applicationId) {
        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);
        if(entry.isPresent()) {
            return modelMapper.map(entry, EntryDTO.Response.class);
        } else {
            return null;
        }
    }

    @Override
    public EntryDTO.UpdateResponse update(Long entryId, EntryDTO.Request request) {
        // entry 존재 유무
        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        // beforeAmount -> afterAmount
        BigDecimal beforeEntryAmount = entry.getEntryAmount();
        entry.setEntryAmount(request.getEntryAmount());
        Entry saved = entryRepository.save(entry);
        // balance update
        balanceService.update(
                entry.getApplicationId(),
                BalanceDTO.UpdateRequest.builder().beforeEntryAmount(beforeEntryAmount)
                        .afterEntryAmount(saved.getEntryAmount())
                        .build()
        );
        // response 반환
        return EntryDTO.UpdateResponse.builder()
                .applicationId(saved.getApplicationId())
                .beforeEntryAmount(beforeEntryAmount)
                .afterEntryAmount(request.getEntryAmount())
                .entryId(saved.getEntryId())
                .build();
    }

    @Override
    public void delete(Long entryId) {
        // entry soft delete
        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        entry.setIsDeleted(true);
        entryRepository.save(entry);

        // balance 0원으로 셋팅
        Long applicationId = entry.getApplicationId();
        balanceService.update(
                applicationId,
                BalanceDTO.UpdateRequest.builder()
                        .beforeEntryAmount(entry.getEntryAmount())
                        .afterEntryAmount(BigDecimal.ZERO)
                        .applicationId(applicationId)
                        .build()
        );
    }

    private boolean isContractApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElse(null);
        return application != null && application.getContractedAt() != null;
    }
}
