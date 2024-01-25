package com.example.fastloan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false") //
public class Terms extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long termsId;

    @Column(columnDefinition = "varchar(255) NOT NULL COMMENT '약관명'")
    private String name;

    @Column(columnDefinition = "varchar(255) NOT NULL COMMENT '약관 상세 URL'")
    private String termsDetailUrl;
}
