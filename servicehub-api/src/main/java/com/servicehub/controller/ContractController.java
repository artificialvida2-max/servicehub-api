package com.servicehub.controller;

import com.servicehub.model.Contract;
import com.servicehub.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contracts")
@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;

    @Operation(summary = "Listar contracts")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Contract> listar() {
        return service.listar();
    }

    @Operation(summary = "Criar contract")
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public Contract criar(@RequestBody Contract c) {
        return service.criar(c);
    }

    @Operation(summary = "Buscar contract por id")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Contract buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @Operation(summary = "Remover contract")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
