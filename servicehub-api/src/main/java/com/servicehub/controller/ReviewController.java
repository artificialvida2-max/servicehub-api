package com.servicehub.controller;
import com.servicehub.model.Review; import com.servicehub.service.ReviewService; import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*; import java.util.List;
@Tag(name = "Reviews")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService service;
    public ReviewController(ReviewService service) { this.service = service; }
    @Operation(summary = "Listar reviews")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "404", description = "Não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping public List<Review> listar() { return service.listar(); }
    @Operation(summary = "Criar review")
    @PostMapping public Review criar(@RequestBody Review r) { return service.criar(r); }
    @Operation(summary = "Buscar review por id")
    @GetMapping("/{id}") public Review buscar(@PathVariable Long id) { return service.buscar(id); }
}
