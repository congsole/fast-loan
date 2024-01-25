package com.example.fastloan.repository;

import com.example.fastloan.domain.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {
}
