package com.servicehub.model;

import jakarta.persistence.*; import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotBlank; import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

// Entidade Provider - prestador de serviço
@Entity @Table(name = "providers") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Provider {
    // ID gerado automaticamente
    @Schema(description = "ID gerado automaticamente")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Nome do prestador
    @NotBlank
    @Schema(description = "Nome do prestador")
    private String name;
    @NotBlank @Email
    private String email;
    // Telefone
    private String phone;
}
