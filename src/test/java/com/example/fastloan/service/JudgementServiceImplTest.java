package com.example.fastloan.service;

import com.example.fastloan.domain.Application;
import com.example.fastloan.domain.Judgement;
import com.example.fastloan.dto.ApplicationDTO;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.JudgementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class JudgementServiceImplTest {
    @InjectMocks
    JudgementServiceImpl judgementService;

    @Mock
    private JudgementRepository judgementRepository;
    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnUpdateResponseOfExistingApplicationEntity_When_RequestGrantApprovalAmountOfJudgementInfo() {
        Judgement judgement = Judgement.builder()
                .name("심사위원이름")
                .applicationId(1L)
                .approvalAmount(BigDecimal.valueOf(5000.00))
                .judgementId(1L)
                .build();

        Application application = Application.builder()
                .applicationId(1L)
                .build();

        when(judgementRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(judgement));
        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(application));
        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(application);

        ApplicationDTO.GrantAmount actual = judgementService.grant(1L);

        assertThat(application.getApprovalAmount()).isEqualTo(judgement.getApprovalAmount());
    }
}