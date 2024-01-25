package com.example.fastloan.repository;

import com.example.fastloan.domain.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
}
