package com.servicehub.service;

import com.servicehub.model.Provider;
import com.servicehub.repository.ProviderRepository;
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
public class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    @Test
    @DisplayName("deve retornar provider quando id existe")
    void deveRetornarProviderQuandoIdExiste() {
        // Arrange
        Provider provider = Provider.builder().id(1L).name("João Silva").email("joao@email.com").build();
        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        // Act
        Provider resultado = providerService.buscar(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getName()).isEqualTo("João Silva");
        verify(providerRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("deve retornar null quando provider não encontrado")
    void deveRetornarNullQuandoProviderNaoEncontrado() {
        // Arrange
        when(providerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Provider resultado = providerService.buscar(99L);

        // Assert
        assertThat(resultado).isNull();
        verify(providerRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("deve criar provider com sucesso")
    void deveCriarProviderComSucesso() {
        // Arrange
        Provider provider = Provider.builder().name("Maria").email("maria@email.com").build();
        when(providerRepository.save(any(Provider.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Provider salvo = providerService.criar(provider);

        // Assert
        assertThat(salvo.getName()).isEqualTo("Maria");
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    @DisplayName("deve listar todos os providers")
    void deveListarTodosOsProviders() {
        // Arrange
        Provider p = Provider.builder().id(1L).name("Ana").build();
        when(providerRepository.findAll()).thenReturn(Collections.singletonList(p));

        // Act
        var resultado = providerService.listar();

        // Assert
        assertThat(resultado).hasSize(1);
        verify(providerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("deve atualizar provider com sucesso")
    void deveAtualizarProviderComSucesso() {
        // Arrange
        Provider existente = Provider.builder().id(1L).name("Antigo").build();
        Provider atualizado = Provider.builder().name("Novo").build();
        when(providerRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(providerRepository.save(any(Provider.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Provider resultado = providerService.atualizar(1L, atualizado);

        // Assert
        assertThat(resultado.getName()).isEqualTo("Novo");
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    @DisplayName("deve deletar provider quando existe")
    void deveDeletarProviderQuandoExiste() {
        // Arrange
        when(providerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(providerRepository).deleteById(1L);

        // Act
        boolean resultado = providerService.remover(1L);

        // Assert
        assertThat(resultado).isTrue();
        verify(providerRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deve lançar exceção quando provider não encontrado para atualização")
    void deveLancarExcecaoQuandoProviderNaoEncontrado() {
        // Arrange
        when(providerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> providerService.atualizar(99L, new Provider()))
                .isInstanceOf(RuntimeException.class);
    }
}
