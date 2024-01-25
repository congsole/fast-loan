package com.example.fastloan.service;

import com.example.fastloan.domain.AcceptTerms;
import com.example.fastloan.domain.Application;
import com.example.fastloan.domain.Terms;
import com.example.fastloan.dto.ApplicationDTO;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.repository.AcceptTermsRepository;
import com.example.fastloan.repository.ApplicationRepository;
import com.example.fastloan.repository.TermsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private TermsRepository termsRepository;
    @Mock
    private AcceptTermsRepository acceptTermsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_AddAcceptTerms_When_RequestAcceptTermsOfApplication() {
        Application application = Application.builder()
                .applicationId(1L)
                .build();

        Terms terms1 = Terms.builder()
                .termsId(1L)
                .build();
        Terms terms2 = Terms.builder()
                .termsId(2L)
                .build();
        Terms terms3 = Terms.builder()
                .termsId(3L)
                .build();


        ApplicationDTO.AcceptTerms request = ApplicationDTO.AcceptTerms.builder()
                .acceptTermsId(Arrays.asList(3L, 2L, 1L))
                .build();

        when(applicationRepository.findById(application.getApplicationId())).thenReturn(Optional.ofNullable(application));
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(List.of(terms1, terms2, terms3));

        Boolean actual = applicationService.acceptTerms(application.getApplicationId(), request);

//        when(acceptTermsRepository.save(any(AcceptTerms.class))).thenReturn(AcceptTerms.builder().termsId(1L).build());

        assertThat(actual).isTrue();
    }

    @Test
    void Should_AddAcceptTermsFail1_When_RequestAcceptTermsOfApplication() {
        Application application = Application.builder()
                .applicationId(1L)
                .build();

        Terms terms1 = Terms.builder()
                .termsId(1L)
                .build();
        Terms terms2 = Terms.builder()
                .termsId(2L)
                .build();
        Terms terms3 = Terms.builder()
                .termsId(3L)
                .build();


        ApplicationDTO.AcceptTerms request = ApplicationDTO.AcceptTerms.builder()
                .acceptTermsId(Arrays.asList(4L, 2L, 1L))
                .build();

        when(applicationRepository.findById(application.getApplicationId())).thenReturn(Optional.ofNullable(application));
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(List.of(terms1, terms2, terms3));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(application.getApplicationId(), request));
    }
    @Test
    void Should_AddAcceptTermsFail2_When_RequestAcceptTermsOfApplication() {
        Application application = Application.builder()
                .applicationId(1L)
                .build();

        Terms terms1 = Terms.builder()
                .termsId(1L)
                .build();
        Terms terms2 = Terms.builder()
                .termsId(2L)
                .build();
        Terms terms3 = Terms.builder()
                .termsId(3L)
                .build();
        Terms terms4 = Terms.builder()
                .termsId(4L)
                .build();


        ApplicationDTO.AcceptTerms request = ApplicationDTO.AcceptTerms.builder()
                .acceptTermsId(Arrays.asList(3L, 2L, 1L))
                .build();

        when(applicationRepository.findById(application.getApplicationId())).thenReturn(Optional.ofNullable(application));
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(List.of(terms1, terms2, terms3, terms4));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(application.getApplicationId(), request));
    }



}