package com.servicehub.controller;

import com.servicehub.dto.ClientRequest;
import com.servicehub.model.Client;
import com.servicehub.service.ClientService;
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

@Tag(name = "Clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @Operation(summary = "Listar clients")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Client> listar() {
        return service.listar();
    }

    @Operation(summary = "Criar client")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Client> criar(@Valid @RequestBody ClientRequest req) {
        Client c = new Client();
        c.setName(req.getName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(c));
    }

    @Operation(summary = "Buscar client por id")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Client buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}
