package com.servicehub.controller;

import com.servicehub.dto.ProviderRequest;
import com.servicehub.model.Provider;
import com.servicehub.service.ProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Providers")
@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService service;

    @Operation(summary = "Listar providers")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Provider>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Criar provider")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')")
    public ResponseEntity<Provider> criar(@Valid @RequestBody ProviderRequest req) {
        Provider p = new Provider();
        p.setName(req.getName());
        p.setEmail(req.getEmail());
        p.setPhone(req.getPhone());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(p));
    }

    @Operation(summary = "Buscar provider por id")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Provider> buscar(@PathVariable Long id) {
        Provider p = service.buscar(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualizar provider")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')")
    public ResponseEntity<Provider> atualizar(@PathVariable Long id, @Valid @RequestBody ProviderRequest req) {
        Provider p = new Provider();
        p.setName(req.getName());
        p.setEmail(req.getEmail());
        p.setPhone(req.getPhone());
        Provider atualizado = service.atualizar(id, p);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualizar parcialmente provider")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')")
    public ResponseEntity<Provider> atualizarParcial(@PathVariable Long id, @Valid @RequestBody ProviderRequest req) {
        Provider p = new Provider();
        p.setName(req.getName());
        p.setEmail(req.getEmail());
        p.setPhone(req.getPhone());
        Provider atualizado = service.atualizarParcial(id, p);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Remover provider")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return service.remover(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
