package com.servicehub.service;

import com.servicehub.model.Provider;
import com.servicehub.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceUnitTest {

    @Mock
    private ProviderRepository repo;

    @InjectMocks
    private ProviderService service;

    @Test
    void listar_deveRetornarListaDeProviders() {
        Provider p = Provider.builder().id(1L).name("João").email("joao@email.com").build();
        when(repo.findAll()).thenReturn(Collections.singletonList(p));

        var resultado = service.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getName()).isEqualTo("João");
        verify(repo).findAll();
    }

    @Test
    void criar_deveSalvarProviderSanitizado() {
        Provider p = Provider.builder().name("<script>Maria</script>").email("maria@email.com").build();
        when(repo.save(any(Provider.class))).thenAnswer(i -> i.getArgument(0));

        Provider salvo = service.criar(p);

        assertThat(salvo.getName()).isEqualTo("&lt;script&gt;Maria&lt;/script&gt;");
        verify(repo).save(p);
    }

    @Test
    void buscar_deveRetornarProviderQuandoExiste() {
        Provider p = Provider.builder().id(1L).name("Ana").build();
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        Provider resultado = service.buscar(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getName()).isEqualTo("Ana");
    }

    @Test
    void buscar_deveRetornarNullQuandoNaoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Provider resultado = service.buscar(99L);

        assertThat(resultado).isNull();
    }
}
