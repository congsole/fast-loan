package com.example.fastloan.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.fastloan.domain.Counsel;
import com.example.fastloan.dto.CounselDTO.*;
import com.example.fastloan.exception.BaseException;
import com.example.fastloan.exception.ResultType;
import com.example.fastloan.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class CounselServiceImplTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("member kim")
                .cellPhone("010-1111-2222")
                .email("abc@def.g")
                .memo("저는 대출을 받고 싶어요. 연락을 주세요")
                .zipCode("12345")
                .address("서울특별시 어딘구 모른동")
                .addressDetail("101동 101호")
                .build();

        Request request = Request.builder()
                .name("member kim")
                .cellPhone("010-1111-2222")
                .email("abc@def.g")
                .memo("저는 대출을 받고 싶어요. 연락을 주세요")
                .zipCode("12345")
                .address("서울특별시 어딘구 모른동")
                .addressDetail("101동 101호")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }
    @Test
    void should_returnExistingCounselEntity_when_requestExistingCounselId() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);
        assertThat(actual.getCounselId()).isSameAs(findId);
    }
    @Test
    void should_throwException_when_requestNotExistingCounselId() {
        Long findId = 2L;
        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        Assertions.assertThrows(BaseException.class, ()->counselService.get(findId));
    }

    @Test
    void Should_ReturnUpdatedResponseofExistingCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("member kim")
                .build();

        Request request = Request.builder()
                .name("member kang")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.update(findId, request);
        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }

    @Test
    void Should_deletedCounselEntity_when_requestExistingDeletedCounselInfo() {
        Long targetId = 1L;
        Counsel entity = Counsel.builder()
                .counselId(1L).build();
        counselService.delete(targetId);

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        assertThat(entity.getIsDeleted()).isSameAs(true);

    }
}