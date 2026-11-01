package com.servicehub.model;

import jakarta.persistence.*; import lombok.*;

@Entity @Table(name = "reviews") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "contract_id") private Contract contract;
    private Integer rating; private String comment;
}
