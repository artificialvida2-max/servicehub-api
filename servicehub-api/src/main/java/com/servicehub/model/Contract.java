package com.servicehub.model;

import jakarta.persistence.*; import lombok.*; import java.time.LocalDate;

@Entity @Table(name = "contracts") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "provider_id") private Provider provider;
    @ManyToOne @JoinColumn(name = "client_id") private Client client;
    @Enumerated(EnumType.STRING) private ServiceCategory serviceCategory;
    private LocalDate startDate; private LocalDate endDate; private String description;
}
