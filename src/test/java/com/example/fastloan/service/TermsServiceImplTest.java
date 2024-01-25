package com.example.fastloan.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.fastloan.domain.Terms;
import com.example.fastloan.dto.TermsDTO.*;
import com.example.fastloan.repository.TermsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;



@ExtendWith(MockitoExtension.class)
class TermsServiceImplTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnAllResponseOfExistingTermsEntities_When_RequestTermsList() {
        Terms term1 = Terms.builder()
                .termsId(1L)
                .name("대출 이용약관 1")
                .termsDetailUrl("https://solhe.congsole.com/1")
                .build();

        Terms term2 = Terms.builder()
                .termsId(2L)
                .name("대출 이용약관 2")
                .termsDetailUrl("https://solhe.congsole.com/2")
                .build();


        List<Terms> list = List.of(term1, term2);
        when(termsRepository.findAll()).thenReturn(list);

        List<Response> actualList = termsService.getAll();

        assertThat(actualList.size()).isSameAs(2);
        assertThat(actualList.get(0).getName()).isSameAs(term1.getName());
        assertThat(actualList.get(1).getName()).isSameAs(term2.getName());

    }
}