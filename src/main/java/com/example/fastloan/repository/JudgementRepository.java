package com.example.fastloan.repository;

import com.example.fastloan.domain.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
    Optional<Judgement> findByApplicationId(Long applicationId);
}
