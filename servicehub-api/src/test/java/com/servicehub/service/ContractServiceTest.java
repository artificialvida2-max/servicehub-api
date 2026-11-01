package com.servicehub.service;

import com.servicehub.model.Contract;
import com.servicehub.repository.ContractRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractService contractService;

    @Test
    @DisplayName("deve criar contrato com sucesso")
    void deveCriarContratoComSucesso() {
        // Arrange
        Contract c = Contract.builder().id(1L).description("Serviço de limpeza").build();
        when(contractRepository.save(any(Contract.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Contract salvo = contractService.criar(c);

        // Assert
        assertThat(salvo.getDescription()).isEqualTo("Serviço de limpeza");
        verify(contractRepository, times(1)).save(any(Contract.class));
    }

    @Test
    @DisplayName("deve retornar contrato quando id existe")
    void deveRetornarContratoQuandoIdExiste() {
        // Arrange
        Contract c = Contract.builder().id(1L).description("Manutenção").build();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(c));

        // Act
        Contract resultado = contractService.buscar(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescription()).isEqualTo("Manutenção");
        verify(contractRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("deve listar todos os contratos")
    void deveListarTodosOsContratos() {
        // Arrange
        Contract c = Contract.builder().id(1L).build();
        when(contractRepository.findAll()).thenReturn(Collections.singletonList(c));

        // Act
        var resultado = contractService.listar();

        // Assert
        assertThat(resultado).hasSize(1);
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("deve deletar contrato quando existe")
    void deveDeletarContratoQuandoExiste() {
        // Arrange
        when(contractRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contractRepository).deleteById(1L);

        // Act
        boolean resultado = contractService.remover(1L);

        // Assert
        assertThat(resultado).isTrue();
        verify(contractRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deve lançar exceção quando contrato não encontrado")
    void deveLancarExcecaoQuandoContratoNaoEncontrado() {
        // Arrange
        when(contractRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> contractService.buscar(99L))
                .isInstanceOf(RuntimeException.class);
    }
}
